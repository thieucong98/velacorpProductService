package com.velacorp.product.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.velacorp.product.domain.enumeration.DeliveryMethod;
import com.velacorp.product.domain.enumeration.DeliveryStatus;
import com.velacorp.product.domain.enumeration.OrderStatus;
import com.velacorp.product.domain.enumeration.PaymentStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * A Order.
 */
@Table("jhi_order")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Order extends AbstractAuditingEntity<Long> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column("id")
    private Long id;

    @Column("email")
    private String email;

    @Column("shipping_address_id")
    private String shippingAddressId;

    @Column("billing_address_id")
    private String billingAddressId;

    @Column("note")
    private String note;

    @Column("tax")
    private Float tax;

    @Column("discount")
    private Float discount;

    @Column("number_item")
    private Integer numberItem;

    @Column("coupon_code")
    private String couponCode;

    @Column("total_price")
    private BigDecimal totalPrice;

    @Column("delivery_fee")
    private BigDecimal deliveryFee;

    @Column("order_status")
    private OrderStatus orderStatus;

    @Column("delivery_method")
    private DeliveryMethod deliveryMethod;

    @Column("delivery_status")
    private DeliveryStatus deliveryStatus;

    @Column("payment_status")
    private PaymentStatus paymentStatus;

    @Column("payment_id")
    private Long paymentId;

    @Column("checkout_id")
    private String checkoutId;

    @Column("reject_reason")
    private String rejectReason;

    @OneToMany(mappedBy = "orderId", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<OrderItem> orderItems = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Order id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return this.email;
    }

    public Order email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getShippingAddressId() {
        return this.shippingAddressId;
    }

    public Order shippingAddressId(String shippingAddressId) {
        this.setShippingAddressId(shippingAddressId);
        return this;
    }

    public void setShippingAddressId(String shippingAddressId) {
        this.shippingAddressId = shippingAddressId;
    }

    public String getBillingAddressId() {
        return this.billingAddressId;
    }

    public Order billingAddressId(String billingAddressId) {
        this.setBillingAddressId(billingAddressId);
        return this;
    }

    public void setBillingAddressId(String billingAddressId) {
        this.billingAddressId = billingAddressId;
    }

    public String getNote() {
        return this.note;
    }

    public Order note(String note) {
        this.setNote(note);
        return this;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Float getTax() {
        return this.tax;
    }

    public Order tax(Float tax) {
        this.setTax(tax);
        return this;
    }

    public void setTax(Float tax) {
        this.tax = tax;
    }

    public Float getDiscount() {
        return this.discount;
    }

    public Order discount(Float discount) {
        this.setDiscount(discount);
        return this;
    }

    public void setDiscount(Float discount) {
        this.discount = discount;
    }

    public Integer getNumberItem() {
        return this.numberItem;
    }

    public Order numberItem(Integer numberItem) {
        this.setNumberItem(numberItem);
        return this;
    }

    public void setNumberItem(Integer numberItem) {
        this.numberItem = numberItem;
    }

    public String getCouponCode() {
        return this.couponCode;
    }

    public Order couponCode(String couponCode) {
        this.setCouponCode(couponCode);
        return this;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public BigDecimal getTotalPrice() {
        return this.totalPrice;
    }

    public Order totalPrice(BigDecimal totalPrice) {
        this.setTotalPrice(totalPrice);
        return this;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice != null ? totalPrice.stripTrailingZeros() : null;
    }

    public BigDecimal getDeliveryFee() {
        return this.deliveryFee;
    }

    public Order deliveryFee(BigDecimal deliveryFee) {
        this.setDeliveryFee(deliveryFee);
        return this;
    }

    public void setDeliveryFee(BigDecimal deliveryFee) {
        this.deliveryFee = deliveryFee != null ? deliveryFee.stripTrailingZeros() : null;
    }

    public OrderStatus getOrderStatus() {
        return this.orderStatus;
    }

    public Order orderStatus(OrderStatus orderStatus) {
        this.setOrderStatus(orderStatus);
        return this;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public DeliveryMethod getDeliveryMethod() {
        return this.deliveryMethod;
    }

    public Order deliveryMethod(DeliveryMethod deliveryMethod) {
        this.setDeliveryMethod(deliveryMethod);
        return this;
    }

    public void setDeliveryMethod(DeliveryMethod deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
    }

    public DeliveryStatus getDeliveryStatus() {
        return this.deliveryStatus;
    }

    public Order deliveryStatus(DeliveryStatus deliveryStatus) {
        this.setDeliveryStatus(deliveryStatus);
        return this;
    }

    public void setDeliveryStatus(DeliveryStatus deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public PaymentStatus getPaymentStatus() {
        return this.paymentStatus;
    }

    public Order paymentStatus(PaymentStatus paymentStatus) {
        this.setPaymentStatus(paymentStatus);
        return this;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Long getPaymentId() {
        return this.paymentId;
    }

    public Order paymentId(Long paymentId) {
        this.setPaymentId(paymentId);
        return this;
    }

    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }

    public String getCheckoutId() {
        return this.checkoutId;
    }

    public Order checkoutId(String checkoutId) {
        this.setCheckoutId(checkoutId);
        return this;
    }

    public void setCheckoutId(String checkoutId) {
        this.checkoutId = checkoutId;
    }

    public String getRejectReason() {
        return this.rejectReason;
    }

    public Order rejectReason(String rejectReason) {
        this.setRejectReason(rejectReason);
        return this;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }

    public Set<OrderItem> getOrderItems() {
        return this.orderItems;
    }

    public void setOrderItems(Set<OrderItem> orderItems) {
        if (this.orderItems != null) {
            this.orderItems.forEach(i -> i.setOrder(null));
        }
        if (orderItems != null) {
            orderItems.forEach(i -> i.setOrder(this));
        }
        this.orderItems = orderItems;
    }

    public Order orderItems(Set<OrderItem> orderItems) {
        this.setOrderItems(orderItems);
        return this;
    }

    public Order addOrderItem(OrderItem orderItem) {
        this.orderItems.add(orderItem);
        orderItem.setOrder(this);
        return this;
    }

    public Order removeOrderItem(OrderItem orderItem) {
        this.orderItems.remove(orderItem);
        orderItem.setOrder(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Order)) {
            return false;
        }
        return getId() != null && getId().equals(((Order) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Order{" +
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
