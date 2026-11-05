
package com.ecommerce.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.dto.OrderRequest;
import com.ecommerce.dto.OrderItemRequest;
import com.ecommerce.entity.Order;
import com.ecommerce.entity.OrderItem;
import com.ecommerce.entity.Product;
import com.ecommerce.entity.User;
import com.ecommerce.repository.OrderRepository;
import com.ecommerce.repository.OrderItemRepository;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.repository.UserRepository;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public OrderService(OrderRepository orderRepository,
            OrderItemRepository orderItemRepository,
            ProductRepository productRepository,
            UserRepository userRepository) {

        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Order createOrder(OrderRequest request) {

        System.out.println("========== CREATE ORDER STARTED ==========");

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Order order = new Order();
        order.setUser(user);
        order.setStatus("PENDING");
        order.setTotalAmount(BigDecimal.ZERO);

        order = orderRepository.save(order);

        BigDecimal totalAmount = BigDecimal.ZERO;
        List<OrderItem> orderItems = new ArrayList<>();
        Set<Long> productIds = new HashSet<>();

        for (OrderItemRequest itemRequest : request.getItems()) {

            if (!productIds.add(itemRequest.getProductId())) {
                throw new RuntimeException("Duplicate product in order");
            }

            if (itemRequest.getQuantity() == null
                    || itemRequest.getQuantity() <= 0) {
                throw new RuntimeException("Quantity must be greater than zero");
            }

            Product product = productRepository
                    .findById(itemRequest.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            System.out.println("Product: " + product.getProductName());
            System.out.println("Previous Stock: " + product.getStockQuantity());

            if (!product.isActive()) {
                throw new RuntimeException("Product is not available");
            }

            if (product.getStockQuantity() < itemRequest.getQuantity()) {
                throw new RuntimeException("Insufficient product stock");
            }

            int remainingStock = product.getStockQuantity()
                    - itemRequest.getQuantity();

            product.setStockQuantity(remainingStock);

            productRepository.saveAndFlush(product);

            System.out.println("STOCK UPDATED: " + product.getProductName()
                    + " | Remaining Stock: " + product.getStockQuantity());

            OrderItem item = new OrderItem();
            item.setOrder(order);
            item.setProduct(product);
            item.setQuantity(itemRequest.getQuantity());
            item.setPrice(product.getPrice());

            BigDecimal subtotal = product.getPrice()
                    .multiply(BigDecimal.valueOf(itemRequest.getQuantity()));

            totalAmount = totalAmount.add(subtotal);
            orderItems.add(item);
        }

        orderItemRepository.saveAll(orderItems);

        order.setTotalAmount(totalAmount);

        Order savedOrder = orderRepository.save(order);

        System.out.println("========== CREATE ORDER COMPLETED ==========");

        return savedOrder;
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    public List<Order> getOrdersByUserId(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return orderRepository.findByUser(user);
    }

    public List<OrderItem> getOrderItems(Long orderId) {
        Order order = getOrderById(orderId);
        return orderItemRepository.findByOrder(order);
    }
}

