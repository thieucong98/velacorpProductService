package com.velacorp.product.repository.rowmapper;

import com.velacorp.product.domain.Order;
import com.velacorp.product.domain.enumeration.DeliveryMethod;
import com.velacorp.product.domain.enumeration.DeliveryStatus;
import com.velacorp.product.domain.enumeration.OrderStatus;
import com.velacorp.product.domain.enumeration.PaymentStatus;
import io.r2dbc.spi.Row;
import java.math.BigDecimal;
import java.util.function.BiFunction;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link Order}, with proper type conversions.
 */
@Service
public class OrderRowMapper implements BiFunction<Row, String, Order> {

    private final ColumnConverter converter;

    public OrderRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link Order} stored in the database.
     */
    @Override
    public Order apply(Row row, String prefix) {
        Order entity = new Order();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setEmail(converter.fromRow(row, prefix + "_email", String.class));
        entity.setShippingAddressId(converter.fromRow(row, prefix + "_shipping_address_id", String.class));
        entity.setBillingAddressId(converter.fromRow(row, prefix + "_billing_address_id", String.class));
        entity.setNote(converter.fromRow(row, prefix + "_note", String.class));
        entity.setTax(converter.fromRow(row, prefix + "_tax", Float.class));
        entity.setDiscount(converter.fromRow(row, prefix + "_discount", Float.class));
        entity.setNumberItem(converter.fromRow(row, prefix + "_number_item", Integer.class));
        entity.setCouponCode(converter.fromRow(row, prefix + "_coupon_code", String.class));
        entity.setTotalPrice(converter.fromRow(row, prefix + "_total_price", BigDecimal.class));
        entity.setDeliveryFee(converter.fromRow(row, prefix + "_delivery_fee", BigDecimal.class));
        entity.setOrderStatus(converter.fromRow(row, prefix + "_order_status", OrderStatus.class));
        entity.setDeliveryMethod(converter.fromRow(row, prefix + "_delivery_method", DeliveryMethod.class));
        entity.setDeliveryStatus(converter.fromRow(row, prefix + "_delivery_status", DeliveryStatus.class));
        entity.setPaymentStatus(converter.fromRow(row, prefix + "_payment_status", PaymentStatus.class));
        entity.setPaymentId(converter.fromRow(row, prefix + "_payment_id", Long.class));
        entity.setCheckoutId(converter.fromRow(row, prefix + "_checkout_id", String.class));
        entity.setRejectReason(converter.fromRow(row, prefix + "_reject_reason", String.class));
        return entity;
    }
}
