
package com.ecommerce.dto;

import java.time.LocalDateTime;

public class DeliveryResponse {

    private Long id;
    private Long orderId;
    private Long deliveryStaffId;
    private String deliveryStaffName;
    private String deliveryAddress;
    private String deliveryStatus;
    private LocalDateTime assignedDate;
    private LocalDateTime deliveredDate;

    public DeliveryResponse() {
    }

    public DeliveryResponse(Long id, Long orderId,
            Long deliveryStaffId, String deliveryStaffName,
            String deliveryAddress, String deliveryStatus,
            LocalDateTime assignedDate, LocalDateTime deliveredDate) {

        this.id = id;
        this.orderId = orderId;
        this.deliveryStaffId = deliveryStaffId;
        this.deliveryStaffName = deliveryStaffName;
        this.deliveryAddress = deliveryAddress;
        this.deliveryStatus = deliveryStatus;
        this.assignedDate = assignedDate;
        this.deliveredDate = deliveredDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getDeliveryStaffName() {
        return deliveryStaffName;
    }

    public void setDeliveryStaffName(String deliveryStaffName) {
        this.deliveryStaffName = deliveryStaffName;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(String deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public LocalDateTime getAssignedDate() {
        return assignedDate;
    }

    public void setAssignedDate(LocalDateTime assignedDate) {
        this.assignedDate = assignedDate;
    }

    public LocalDateTime getDeliveredDate() {
        return deliveredDate;
    }

    public void setDeliveredDate(LocalDateTime deliveredDate) {
        this.deliveredDate = deliveredDate;
    }
}
