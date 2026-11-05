
package com.ecommerce.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "deliveries")
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "order_id", nullable = false, unique = true)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "delivery_staff_id")
    private User deliveryStaff;

    private String deliveryAddress;

    private String deliveryStatus;

    private LocalDateTime assignedDate;

    private LocalDateTime deliveredDate;

    public Delivery() {
    }

    @PrePersist
    public void onCreate() {
        assignedDate = LocalDateTime.now();

        if (deliveryStatus == null) {
            deliveryStatus = "ASSIGNED";
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public User getDeliveryStaff() {
        return deliveryStaff;
    }

    public void setDeliveryStaff(User deliveryStaff) {
        this.deliveryStaff = deliveryStaff;
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
