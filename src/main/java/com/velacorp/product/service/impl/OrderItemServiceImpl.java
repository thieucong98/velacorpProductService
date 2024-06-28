package com.velacorp.product.service.impl;

import com.velacorp.product.repository.OrderItemRepository;
import com.velacorp.product.service.OrderItemService;
import com.velacorp.product.service.dto.OrderItemDTO;
import com.velacorp.product.service.mapper.OrderItemMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Implementation for managing {@link com.velacorp.product.domain.OrderItem}.
 */
@Service
@Transactional
public class OrderItemServiceImpl implements OrderItemService {

    private static final Logger log = LoggerFactory.getLogger(OrderItemServiceImpl.class);

    private final OrderItemRepository orderItemRepository;

    private final OrderItemMapper orderItemMapper;

    public OrderItemServiceImpl(OrderItemRepository orderItemRepository, OrderItemMapper orderItemMapper) {
        this.orderItemRepository = orderItemRepository;
        this.orderItemMapper = orderItemMapper;
    }

    @Override
    public Mono<OrderItemDTO> save(OrderItemDTO orderItemDTO) {
        log.debug("Request to save OrderItem : {}", orderItemDTO);
        return orderItemRepository.save(orderItemMapper.toEntity(orderItemDTO)).map(orderItemMapper::toDto);
    }

    @Override
    public Mono<OrderItemDTO> update(OrderItemDTO orderItemDTO) {
        log.debug("Request to update OrderItem : {}", orderItemDTO);
        return orderItemRepository.save(orderItemMapper.toEntity(orderItemDTO)).map(orderItemMapper::toDto);
    }

    @Override
    public Mono<OrderItemDTO> partialUpdate(OrderItemDTO orderItemDTO) {
        log.debug("Request to partially update OrderItem : {}", orderItemDTO);

        return orderItemRepository
            .findById(orderItemDTO.getId())
            .map(existingOrderItem -> {
                orderItemMapper.partialUpdate(existingOrderItem, orderItemDTO);

                return existingOrderItem;
            })
            .flatMap(orderItemRepository::save)
            .map(orderItemMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Flux<OrderItemDTO> findAll(Pageable pageable) {
        log.debug("Request to get all OrderItems");
        return orderItemRepository.findAllBy(pageable).map(orderItemMapper::toDto);
    }

    public Flux<OrderItemDTO> findAllWithEagerRelationships(Pageable pageable) {
        return orderItemRepository.findAllWithEagerRelationships(pageable).map(orderItemMapper::toDto);
    }

    public Mono<Long> countAll() {
        return orderItemRepository.count();
    }

    @Override
    @Transactional(readOnly = true)
    public Mono<OrderItemDTO> findOne(Long id) {
        log.debug("Request to get OrderItem : {}", id);
        return orderItemRepository.findOneWithEagerRelationships(id).map(orderItemMapper::toDto);
    }

    @Override
    public Mono<Void> delete(Long id) {
        log.debug("Request to delete OrderItem : {}", id);
        return orderItemRepository.deleteById(id);
    }
}
