
package com.ecommerce.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import com.ecommerce.dto.OrderRequest;
import com.ecommerce.dto.OrderResponse;
import com.ecommerce.dto.OrderItemResponse;
import com.ecommerce.entity.Order;
import com.ecommerce.entity.OrderItem;
import com.ecommerce.service.OrderService;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponse createOrder(
            @Valid @RequestBody OrderRequest request) {

        Order order = orderService.createOrder(request);
        return convertToResponse(order);
    }

    @GetMapping
    public List<OrderResponse> getAllOrders() {
        return orderService.getAllOrders()
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    @GetMapping("/{id}")
    public OrderResponse getOrderById(@PathVariable Long id) {
        Order order = orderService.getOrderById(id);
        return convertToResponse(order);
    }

    @GetMapping("/user/{userId}")
    public List<OrderResponse> getOrdersByUserId(
            @PathVariable Long userId) {

        return orderService.getOrdersByUserId(userId)
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    private OrderResponse convertToResponse(Order order) {

        List<OrderItemResponse> items =
                orderService.getOrderItems(order.getId())
                        .stream()
                        .map(this::convertItemToResponse)
                        .toList();

        return new OrderResponse(
                order.getId(),
                order.getUser().getId(),
                order.getOrderDate(),
                order.getTotalAmount(),
                order.getStatus(),
                items
        );
    }

    private OrderItemResponse convertItemToResponse(OrderItem item) {

        BigDecimal subtotal = item.getPrice()
                .multiply(BigDecimal.valueOf(item.getQuantity()));

        return new OrderItemResponse(
                item.getId(),
                item.getProduct().getId(),
                item.getProduct().getProductName(),
                item.getQuantity(),
                item.getPrice(),
                subtotal
        );
    }
}
