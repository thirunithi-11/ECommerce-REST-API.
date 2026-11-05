
package com.ecommerce.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.entity.Delivery;
import com.ecommerce.entity.Order;
import com.ecommerce.entity.User;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {

    Optional<Delivery> findByOrder(Order order);

    List<Delivery> findByDeliveryStaff(User deliveryStaff);

}
