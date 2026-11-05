package com.ecommerce.dto;

import java.time.LocalDateTime;
import java.util.List;

public class WishlistResponse {

    private Long id;
    private Long userId;
    private LocalDateTime createdAt;
    private List<WishlistItemResponse> items;

    public WishlistResponse() {
    }

    public WishlistResponse(Long id, Long userId,
            LocalDateTime createdAt,
            List<WishlistItemResponse> items) {

        this.id = id;
        this.userId = userId;
        this.createdAt = createdAt;
        this.items = items;
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

    public List<WishlistItemResponse> getItems() {
        return items;
    }

    public void setItems(List<WishlistItemResponse> items) {
        this.items = items;
    }
}