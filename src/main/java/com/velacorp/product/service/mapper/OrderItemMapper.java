package com.velacorp.product.service.mapper;

import com.velacorp.product.domain.Order;
import com.velacorp.product.domain.OrderItem;
import com.velacorp.product.service.dto.OrderDTO;
import com.velacorp.product.service.dto.OrderItemDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link OrderItem} and its DTO {@link OrderItemDTO}.
 */
@Mapper(componentModel = "spring")
public interface OrderItemMapper extends EntityMapper<OrderItemDTO, OrderItem> {}
