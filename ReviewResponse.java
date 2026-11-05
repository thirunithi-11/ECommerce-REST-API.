package com.ecommerce.dto;

import java.time.LocalDateTime;

public class ReviewResponse {

    private Long id;
    private Long userId;
    private String customerName;
    private Long productId;
    private Integer rating;
    private String comment;
    private LocalDateTime createdAt;

    public ReviewResponse(Long id, Long userId, String customerName,
            Long productId, Integer rating, String comment,
            LocalDateTime createdAt) {

        this.id = id;
        this.userId = userId;
        this.customerName = customerName;
        this.productId = productId;
        this.rating = rating;
        this.comment = comment;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public Long getProductId() {
        return productId;
    }

    public Integer getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
