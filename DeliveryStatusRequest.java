
package com.ecommerce.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class DeliveryStatusRequest {

    @NotBlank(message = "Delivery status is required")
    @Pattern(
        regexp = "PICKED_UP|IN_TRANSIT|DELIVERED|FAILED",
        message = "Invalid delivery status"
    )
    private String deliveryStatus;

    public DeliveryStatusRequest() {
    }

    public String getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(String deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }
}
