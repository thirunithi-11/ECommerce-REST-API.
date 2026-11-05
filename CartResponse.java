
package com.ecommerce.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class CartResponse {

    private Long id;
    private Long userId;
    private LocalDateTime createdAt;
    private List<CartItemResponse> items;
    private BigDecimal totalAmount;

    public CartResponse() {
    }

    public CartResponse(Long id, Long userId,
            LocalDateTime createdAt,
            List<CartItemResponse> items,
            BigDecimal totalAmount) {

        this.id = id;
        this.userId = userId;
        this.createdAt = createdAt;
        this.items = items;
        this.totalAmount = totalAmount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<CartItemResponse> getItems() {
        return items;
    }

    public void setItems(List<CartItemResponse> items) {
        this.items = items;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }
}
