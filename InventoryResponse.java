
package com.ecommerce.dto;

import java.math.BigDecimal;

public class InventoryResponse {

    private Long productId;
    private String productName;
    private Integer availableQuantity;
    private BigDecimal price;

    public InventoryResponse() {
    }

    public InventoryResponse(Long productId, String productName,
            Integer availableQuantity, BigDecimal price) {
        this.productId = productId;
        this.productName = productName;
        this.availableQuantity = availableQuantity;
        this.price = price;
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

    public Integer getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(Integer availableQuantity) {
        this.availableQuantity = availableQuantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}

