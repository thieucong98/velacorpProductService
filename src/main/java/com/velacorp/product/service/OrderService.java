package com.velacorp.product.service;

import com.velacorp.product.domain.Order;
import com.velacorp.product.domain.OrderItem;
import com.velacorp.product.repository.OrderItemRepository;
import com.velacorp.product.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Implementation for managing {@link com.velacorp.product.domain.Order}.
 */
@Service
@Transactional
public class OrderService {

    private static final Logger log = LoggerFactory.getLogger(OrderService.class);

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    public OrderService(OrderRepository orderRepository, OrderItemRepository orderItemRepository) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
    }

    /**
     * Save a order.
     *
     * @param order the entity to save.
     * @return the persisted entity.
     */
    public Mono<Order> save(Order order) {
        log.debug("Request to save Order : {}", order);
        return orderRepository.save(order);
    }

    /**
     * Update a order.
     *
     * @param order the entity to save.
     * @return the persisted entity.
     */
    public Mono<Order> update(Order order) {
        log.debug("Request to update Order : {}", order);
        return orderRepository.save(order);
    }

    /**
     * Partially update a order.
     *
     * @param order the entity to update partially.
     * @return the persisted entity.
     */
    public Mono<Order> partialUpdate(Order order) {
        log.debug("Request to partially update Order : {}", order);

        return orderRepository
            .findById(order.getId())
            .map(existingOrder -> {
                if (order.getEmail() != null) {
                    existingOrder.setEmail(order.getEmail());
                }
                if (order.getShippingAddressId() != null) {
                    existingOrder.setShippingAddressId(order.getShippingAddressId());
                }
                if (order.getBillingAddressId() != null) {
                    existingOrder.setBillingAddressId(order.getBillingAddressId());
                }
                if (order.getNote() != null) {
                    existingOrder.setNote(order.getNote());
                }
                if (order.getTax() != null) {
                    existingOrder.setTax(order.getTax());
                }
                if (order.getDiscount() != null) {
                    existingOrder.setDiscount(order.getDiscount());
                }
                if (order.getNumberItem() != null) {
                    existingOrder.setNumberItem(order.getNumberItem());
                }
                if (order.getCouponCode() != null) {
                    existingOrder.setCouponCode(order.getCouponCode());
                }
                if (order.getTotalPrice() != null) {
                    existingOrder.setTotalPrice(order.getTotalPrice());
                }
                if (order.getDeliveryFee() != null) {
                    existingOrder.setDeliveryFee(order.getDeliveryFee());
                }
                if (order.getOrderStatus() != null) {
                    existingOrder.setOrderStatus(order.getOrderStatus());
                }
                if (order.getDeliveryMethod() != null) {
                    existingOrder.setDeliveryMethod(order.getDeliveryMethod());
                }
                if (order.getDeliveryStatus() != null) {
                    existingOrder.setDeliveryStatus(order.getDeliveryStatus());
                }
                if (order.getPaymentStatus() != null) {
                    existingOrder.setPaymentStatus(order.getPaymentStatus());
                }
                if (order.getPaymentId() != null) {
                    existingOrder.setPaymentId(order.getPaymentId());
                }
                if (order.getCheckoutId() != null) {
                    existingOrder.setCheckoutId(order.getCheckoutId());
                }
                if (order.getRejectReason() != null) {
                    existingOrder.setRejectReason(order.getRejectReason());
                }

                return existingOrder;
            })
            .flatMap(orderRepository::save);
    }

    /**
     * Get all the orders.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Flux<Order> findAll(Pageable pageable) {
        log.debug("Request to get all Orders");
        Flux<Order> allBy = orderRepository.findAllBy(pageable);
        return allBy;
    }

    /**
     * Returns the number of orders available.
     *
     * @return the number of entities in the database.
     */
    public Mono<Long> countAll() {
        return orderRepository.count();
    }

    /**
     * Get one order by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Mono<Order> findOne(Long id) {
        log.debug("Request to get Order : {}", id);
        return orderRepository.findById(id).map(this::apply);
    }

    /**
     * Delete the order by id.
     *
     * @param id the id of the entity.
     * @return a Mono to signal the deletion
     */
    public Mono<Void> delete(Long id) {
        log.debug("Request to delete Order : {}", id);
        return orderRepository.deleteById(id);
    }

    private Order apply(Order order) {
        this.orderItemRepository.findByOrder(order.getId())
            .collectList()
            .subscribe(orderItems -> {
                order.getOrderItems().addAll(orderItems);
            });

        return order;
    }
}
