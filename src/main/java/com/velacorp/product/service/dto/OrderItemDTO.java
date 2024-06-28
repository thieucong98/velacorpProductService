package com.velacorp.product.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A DTO for the {@link com.velacorp.product.domain.OrderItem} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class OrderItemDTO implements Serializable {

    private Long id;

    private Long productId;

    private String productName;

    private Integer quantity;

    private BigDecimal productPrice;

    private String note;

    private BigDecimal discountAmount;

    private BigDecimal taxAmount;

    private BigDecimal taxPercent;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
    }

    public BigDecimal getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(BigDecimal taxAmount) {
        this.taxAmount = taxAmount;
    }

    public BigDecimal getTaxPercent() {
        return taxPercent;
    }

    public void setTaxPercent(BigDecimal taxPercent) {
        this.taxPercent = taxPercent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrderItemDTO)) {
            return false;
        }

        OrderItemDTO orderItemDTO = (OrderItemDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, orderItemDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrderItemDTO{" +
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
