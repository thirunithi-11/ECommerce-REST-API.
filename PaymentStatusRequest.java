package com.ecommerce.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class PaymentStatusRequest {

    @NotBlank(message = "Payment status is required")
    @Pattern(
        regexp = "SUCCESS|FAILED",
        message = "Payment status must be SUCCESS or FAILED"
    )
    private String paymentStatus;

    public PaymentStatusRequest() {
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}