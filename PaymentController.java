
package com.ecommerce.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.ecommerce.dto.PaymentRequest;
import com.ecommerce.dto.PaymentResponse;
import com.ecommerce.dto.PaymentStatusRequest;
import com.ecommerce.entity.Payment;
import com.ecommerce.service.PaymentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PaymentResponse createPayment(
            @Valid @RequestBody PaymentRequest request) {

        Payment payment = paymentService.createPayment(request);
        return convertToResponse(payment);
    }

    @GetMapping
    public List<PaymentResponse> getAllPayments() {

        return paymentService.getAllPayments()
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    @GetMapping("/{id}")
    public PaymentResponse getPaymentById(@PathVariable Long id) {

        Payment payment = paymentService.getPaymentById(id);
        return convertToResponse(payment);
    }

    @PatchMapping("/{id}/status")
    public PaymentResponse updatePaymentStatus(
            @PathVariable Long id,
            @Valid @RequestBody PaymentStatusRequest request) {

        Payment payment = paymentService.updatePaymentStatus(id, request);
        return convertToResponse(payment);
    }

    private PaymentResponse convertToResponse(Payment payment) {

        return new PaymentResponse(
                payment.getId(),
                payment.getOrder().getId(),
                payment.getAmount(),
                payment.getPaymentMethod(),
                payment.getPaymentStatus(),
                payment.getTransactionId(),
                payment.getPaymentDate()
        );
    }
}

