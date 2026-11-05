
package com.ecommerce.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class DeliveryRequest {

    @NotNull(message = "Order ID is required")
    @Positive(message = "Order ID must be positive")
    private Long orderId;

    @NotNull(message = "Delivery staff ID is required")
    @Positive(message = "Delivery staff ID must be positive")
    private Long deliveryStaffId;

    @NotBlank(message = "Delivery address is required")
    private String deliveryAddress;

    public DeliveryRequest() {
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getDeliveryStaffId() {
        return deliveryStaffId;
    }

    public void setDeliveryStaffId(Long deliveryStaffId) {
        this.deliveryStaffId = deliveryStaffId;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }
}
