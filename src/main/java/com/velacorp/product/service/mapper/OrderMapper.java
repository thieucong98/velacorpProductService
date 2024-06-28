package com.velacorp.product.service.mapper;

import com.velacorp.product.domain.Order;
import com.velacorp.product.service.dto.OrderDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Order} and its DTO {@link OrderDTO}.
 */
@Mapper(componentModel = "spring")
public interface OrderMapper extends EntityMapper<OrderDTO, Order> {}
