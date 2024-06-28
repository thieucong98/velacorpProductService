package com.velacorp.product.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * A OrderItem.
 */
@Table("order_item")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class OrderItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column("id")
    private Long id;

    @Column("product_id")
    private Long productId;

    @Column("product_name")
    private String productName;

    @Column("quantity")
    private Integer quantity;

    @Column("product_price")
    private BigDecimal productPrice;

    @Column("note")
    private String note;

    @Column("discount_amount")
    private BigDecimal discountAmount;

    @Column("tax_amount")
    private BigDecimal taxAmount;

    @Column("tax_percent")
    private BigDecimal taxPercent;

    @Transient
    @JsonIgnoreProperties(value = { "orderItems" }, allowSetters = true)
    private Order order;

    @Column("order_id")
    private Long orderId;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public OrderItem id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return this.productId;
    }

    public OrderItem productId(Long productId) {
        this.setProductId(productId);
        return this;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return this.productName;
    }

    public OrderItem productName(String productName) {
        this.setProductName(productName);
        return this;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getQuantity() {
        return this.quantity;
    }

    public OrderItem quantity(Integer quantity) {
        this.setQuantity(quantity);
        return this;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getProductPrice() {
        return this.productPrice;
    }

    public OrderItem productPrice(BigDecimal productPrice) {
        this.setProductPrice(productPrice);
        return this;
    }

    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice != null ? productPrice.stripTrailingZeros() : null;
    }

    public String getNote() {
        return this.note;
    }

    public OrderItem note(String note) {
        this.setNote(note);
        return this;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public BigDecimal getDiscountAmount() {
        return this.discountAmount;
    }

    public OrderItem discountAmount(BigDecimal discountAmount) {
        this.setDiscountAmount(discountAmount);
        return this;
    }

    public void setDiscountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount != null ? discountAmount.stripTrailingZeros() : null;
    }

    public BigDecimal getTaxAmount() {
        return this.taxAmount;
    }

    public OrderItem taxAmount(BigDecimal taxAmount) {
        this.setTaxAmount(taxAmount);
        return this;
    }

    public void setTaxAmount(BigDecimal taxAmount) {
        this.taxAmount = taxAmount != null ? taxAmount.stripTrailingZeros() : null;
    }

    public BigDecimal getTaxPercent() {
        return this.taxPercent;
    }

    public OrderItem taxPercent(BigDecimal taxPercent) {
        this.setTaxPercent(taxPercent);
        return this;
    }

    public void setTaxPercent(BigDecimal taxPercent) {
        this.taxPercent = taxPercent != null ? taxPercent.stripTrailingZeros() : null;
    }

    public Order getOrder() {
        return this.order;
    }

    public void setOrder(Order order) {
        this.order = order;
        this.orderId = order != null ? order.getId() : null;
    }

    public OrderItem order(Order order) {
        this.setOrder(order);
        return this;
    }

    public Long getOrderId() {
        return this.orderId;
    }

    public void setOrderId(Long order) {
        this.orderId = order;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrderItem)) {
            return false;
        }
        return getId() != null && getId().equals(((OrderItem) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrderItem{" +
            "id=" + getId() +
            ", productId=" + getProductId() +
            ", productName='" + getProductName() + "'" +
            ", quantity=" + getQuantity() +
            ", productPrice=" + getProductPrice() +
            ", note='" + getNote() + "'" +
            ", discountAmount=" + getDiscountAmount() +
            ", taxAmount=" + getTaxAmount() +
            ", taxPercent=" + getTaxPercent() +
            "}";
    }
}
