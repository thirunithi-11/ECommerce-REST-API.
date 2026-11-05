
package com.ecommerce.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.dto.PaymentRequest;
import com.ecommerce.dto.PaymentStatusRequest;
import com.ecommerce.entity.Order;
import com.ecommerce.entity.Payment;
import com.ecommerce.repository.OrderRepository;
import com.ecommerce.repository.PaymentRepository;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;

    public PaymentService(PaymentRepository paymentRepository,
            OrderRepository orderRepository) {
        this.paymentRepository = paymentRepository;
        this.orderRepository = orderRepository;
    }

    @Transactional
    public Payment createPayment(PaymentRequest request) {

        Order order = orderRepository.findById(request.getOrderId())
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (!"PENDING".equals(order.getStatus())) {
            throw new RuntimeException("Order is not available for payment");
        }

        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setAmount(order.getTotalAmount());
        payment.setPaymentMethod(request.getPaymentMethod());
        payment.setPaymentStatus("PENDING");

        return paymentRepository.save(payment);
    }

    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    public Payment getPaymentById(Long id) {

        return paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
    }

    @Transactional
    public Payment updatePaymentStatus(Long id, PaymentStatusRequest request) {

        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        if (!"PENDING".equals(payment.getPaymentStatus())) {
            throw new RuntimeException("Payment status already updated");
        }

        payment.setPaymentStatus(request.getPaymentStatus());

        return paymentRepository.save(payment);
    }
}
