package com.velacorp.product.service.impl;

import com.velacorp.product.domain.Order;
import com.velacorp.product.domain.OrderItem;
import com.velacorp.product.repository.OrderItemRepository;
import com.velacorp.product.repository.OrderRepository;
import com.velacorp.product.repository.ProductRepository;
import com.velacorp.product.service.OrderService;
import com.velacorp.product.service.dto.OrderDTO;
import com.velacorp.product.service.dto.OrderItemDTO;
import com.velacorp.product.service.mapper.OrderItemMapper;
import com.velacorp.product.service.mapper.OrderMapper;
import com.velacorp.product.service.validator.OrderValidator;
import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

/**
 * Service Implementation for managing {@link com.velacorp.product.domain.Order}.
 */
@Service
@Transactional
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private static final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderValidator orderValidator;
    private final ProductRepository productRepository;

    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;

    @Override
    public Mono<OrderDTO> save(OrderDTO orderDTO) {
        log.debug("Request to save Order : {}", orderDTO);
        return this.orderValidator.validate(orderDTO).then(doSave(orderDTO));
    }

    private @NotNull Mono<OrderDTO> doSave(OrderDTO orderDTO) {
        Order entity = orderMapper.toEntity(orderDTO);
        entity.calculateTotal();
        return orderRepository
            .save(entity)
            .map(orderMapper::toDto)
            .map(order -> {
                Set<OrderItemDTO> orderItems = orderDTO.getOrderItems();
                Set<OrderItem> items = new HashSet<>();
                orderItems.forEach(orderItemDTO -> {
                    OrderItem orderItem = orderItemMapper.toEntity(orderItemDTO);
                    orderItem.setOrderId(order.getId());
                    items.add(orderItem);
                });
                order.getOrderItems().clear();
                orderItemRepository
                    .saveAll(items)
                    .subscribe(orderItem -> {
                        order.getOrderItems().add(orderItemMapper.toDto(orderItem));
                        productRepository
                            .findById(orderItem.getProductId())
                            .subscribe(product -> {
                                product.setQuantity(product.getQuantity() - orderItem.getQuantity());
                                productRepository.save(product).subscribe();
                            });
                    });
                return order;
            });
    }

    @Override
    public Mono<OrderDTO> update(OrderDTO orderDTO) {
        log.debug("Request to update Order : {}", orderDTO);
        return orderRepository.save(orderMapper.toEntity(orderDTO)).map(orderMapper::toDto);
    }

    @Override
    public Mono<OrderDTO> partialUpdate(OrderDTO orderDTO) {
        log.debug("Request to partially update Order : {}", orderDTO);

        return orderRepository
            .findById(orderDTO.getId())
            .map(existingOrder -> {
                orderMapper.partialUpdate(existingOrder, orderDTO);

                return existingOrder;
            })
            .flatMap(orderRepository::save)
            .map(orderMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Flux<OrderDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Orders");
        return orderRepository.findAllBy(pageable).map(orderMapper::toDto);
    }

    public Mono<Long> countAll() {
        return orderRepository.count();
    }

    @Override
    @Transactional(readOnly = true)
    public Mono<OrderDTO> findOne(Long id) {
        log.debug("Request to get Order : {}", id);
        return orderRepository
            .findById(id)
            .map(orderMapper::toDto)
            .publishOn(Schedulers.boundedElastic())
            .map(order -> {
                this.orderItemRepository.findByOrder(order.getId())
                    .map(orderItemMapper::toDto)
                    .collectList()
                    .map(orderItems -> order.getOrderItems().addAll(orderItems))
                    .block();
                return order;
            });
    }

    @Override
    public Mono<Void> delete(Long id) {
        log.debug("Request to delete Order : {}", id);
        return this.orderItemRepository.deleteByOrderId(id).then(this.orderRepository.deleteById(id));
    }
}
