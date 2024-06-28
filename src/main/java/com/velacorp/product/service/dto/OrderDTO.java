package com.velacorp.product.service.dto;

import com.velacorp.product.domain.OrderItem;
import com.velacorp.product.domain.enumeration.DeliveryMethod;
import com.velacorp.product.domain.enumeration.DeliveryStatus;
import com.velacorp.product.domain.enumeration.OrderStatus;
import com.velacorp.product.domain.enumeration.PaymentStatus;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link com.velacorp.product.domain.Order} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class OrderDTO implements Serializable {

    private Long id;

    @NotNull(message = "must not be null")
    private String email;

    private String shippingAddressId;

    private String billingAddressId;

    private String note;

    private Float tax;

    private Float discount;

    private Integer numberItem;

    private String couponCode;

    private BigDecimal totalPrice;

    private BigDecimal deliveryFee;

    private OrderStatus orderStatus;

    private DeliveryMethod deliveryMethod;

    private DeliveryStatus deliveryStatus;

    private PaymentStatus paymentStatus;

    private Long paymentId;

    private String checkoutId;

    private String rejectReason;

    private Set<OrderItemDTO> orderItems = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getShippingAddressId() {
        return shippingAddressId;
    }

    public void setShippingAddressId(String shippingAddressId) {
        this.shippingAddressId = shippingAddressId;
    }

    public String getBillingAddressId() {
        return billingAddressId;
    }

    public void setBillingAddressId(String billingAddressId) {
        this.billingAddressId = billingAddressId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Float getTax() {
        return tax;
    }

    public void setTax(Float tax) {
        this.tax = tax;
    }

    public Float getDiscount() {
        return discount;
    }

    public void setDiscount(Float discount) {
        this.discount = discount;
    }

    public Integer getNumberItem() {
        return numberItem;
    }

    public void setNumberItem(Integer numberItem) {
        this.numberItem = numberItem;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public BigDecimal getDeliveryFee() {
        return deliveryFee;
    }

    public void setDeliveryFee(BigDecimal deliveryFee) {
        this.deliveryFee = deliveryFee;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public DeliveryMethod getDeliveryMethod() {
        return deliveryMethod;
    }

    public void setDeliveryMethod(DeliveryMethod deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
    }

    public DeliveryStatus getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(DeliveryStatus deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }

    public String getCheckoutId() {
        return checkoutId;
    }

    public void setCheckoutId(String checkoutId) {
        this.checkoutId = checkoutId;
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }

    public Set<OrderItemDTO> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(Set<OrderItemDTO> orderItems) {
        this.orderItems = orderItems;
    }

    public OrderDTO addOrderItem(OrderItemDTO orderItem) {
        this.orderItems.add(orderItem);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrderDTO)) {
            return false;
        }

        OrderDTO orderDTO = (OrderDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, orderDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrderDTO{" +
            "id=" + getId() +
            ", email='" + getEmail() + "'" +
            ", shippingAddressId='" + getShippingAddressId() + "'" +
            ", billingAddressId='" + getBillingAddressId() + "'" +
            ", note='" + getNote() + "'" +
            ", tax=" + getTax() +
            ", discount=" + getDiscount() +
            ", numberItem=" + getNumberItem() +
            ", couponCode='" + getCouponCode() + "'" +
            ", totalPrice=" + getTotalPrice() +
            ", deliveryFee=" + getDeliveryFee() +
            ", orderStatus='" + getOrderStatus() + "'" +
            ", deliveryMethod='" + getDeliveryMethod() + "'" +
            ", deliveryStatus='" + getDeliveryStatus() + "'" +
            ", paymentStatus='" + getPaymentStatus() + "'" +
            ", paymentId=" + getPaymentId() +
            ", checkoutId='" + getCheckoutId() + "'" +
            ", rejectReason='" + getRejectReason() + "'" +
            "}";
    }
}
