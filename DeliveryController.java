
package com.ecommerce.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.ecommerce.dto.DeliveryRequest;
import com.ecommerce.dto.DeliveryResponse;
import com.ecommerce.dto.DeliveryStatusRequest;
import com.ecommerce.entity.Delivery;
import com.ecommerce.service.DeliveryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/deliveries")
public class DeliveryController {

    private final DeliveryService deliveryService;

    public DeliveryController(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DeliveryResponse createDelivery(
            @Valid @RequestBody DeliveryRequest request) {

        Delivery delivery = deliveryService.createDelivery(request);
        return convertToResponse(delivery);
    }

    @GetMapping
    public List<DeliveryResponse> getAllDeliveries() {

        return deliveryService.getAllDeliveries()
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    @GetMapping("/{id}")
    public DeliveryResponse getDeliveryById(@PathVariable Long id) {

        Delivery delivery = deliveryService.getDeliveryById(id);
        return convertToResponse(delivery);
    }

    @GetMapping("/staff/{staffId}")
    public List<DeliveryResponse> getDeliveriesByStaff(
            @PathVariable Long staffId) {

        return deliveryService.getDeliveriesByStaff(staffId)
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    @PatchMapping("/{id}/status")
    public DeliveryResponse updateDeliveryStatus(
            @PathVariable Long id,
            @Valid @RequestBody DeliveryStatusRequest request) {

        Delivery delivery =
                deliveryService.updateDeliveryStatus(id, request);

        return convertToResponse(delivery);
    }

    private DeliveryResponse convertToResponse(Delivery delivery) {

        return new DeliveryResponse(
                delivery.getId(),
                delivery.getOrder().getId(),
                delivery.getDeliveryStaff().getId(),
                delivery.getDeliveryStaff().getFullName(),
                delivery.getDeliveryAddress(),
                delivery.getDeliveryStatus(),
                delivery.getAssignedDate(),
                delivery.getDeliveredDate()
        );
    }
}
