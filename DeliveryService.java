
package com.ecommerce.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.dto.DeliveryRequest;
import com.ecommerce.dto.DeliveryStatusRequest;
import com.ecommerce.entity.Delivery;
import com.ecommerce.entity.Order;
import com.ecommerce.entity.User;
import com.ecommerce.repository.DeliveryRepository;
import com.ecommerce.repository.OrderRepository;
import com.ecommerce.repository.UserRepository;

@Service
public class DeliveryService {

    private final DeliveryRepository deliveryRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    public DeliveryService(DeliveryRepository deliveryRepository,
            OrderRepository orderRepository,
            UserRepository userRepository) {

        this.deliveryRepository = deliveryRepository;
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Delivery createDelivery(DeliveryRequest request) {

        Order order = orderRepository.findById(request.getOrderId())
                .orElseThrow(() -> new RuntimeException("Order not found"));

        User deliveryStaff = userRepository.findById(request.getDeliveryStaffId())
                .orElseThrow(() -> new RuntimeException("Delivery staff not found"));

        if (deliveryRepository.findByOrder(order).isPresent()) {
            throw new RuntimeException("Delivery already assigned for this order");
        }

        Delivery delivery = new Delivery();

        delivery.setOrder(order);
        delivery.setDeliveryStaff(deliveryStaff);
        delivery.setDeliveryAddress(request.getDeliveryAddress());
        delivery.setDeliveryStatus("ASSIGNED");

        return deliveryRepository.save(delivery);
    }

    public List<Delivery> getAllDeliveries() {
        return deliveryRepository.findAll();
    }

    public Delivery getDeliveryById(Long id) {

        return deliveryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Delivery not found"));
    }

    public List<Delivery> getDeliveriesByStaff(Long staffId) {

        User deliveryStaff = userRepository.findById(staffId)
                .orElseThrow(() -> new RuntimeException("Delivery staff not found"));

        return deliveryRepository.findByDeliveryStaff(deliveryStaff);
    }

    @Transactional
    public Delivery updateDeliveryStatus(
            Long id, DeliveryStatusRequest request) {

        Delivery delivery = deliveryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Delivery not found"));

        String currentStatus = delivery.getDeliveryStatus();
        String newStatus = request.getDeliveryStatus();

        if ("DELIVERED".equals(currentStatus)
                || "FAILED".equals(currentStatus)) {
            throw new RuntimeException("Delivery is already completed");
        }

        if ("DELIVERED".equals(newStatus)) {
            delivery.setDeliveredDate(LocalDateTime.now());
        }

        delivery.setDeliveryStatus(newStatus);

        return deliveryRepository.save(delivery);
    }
}

