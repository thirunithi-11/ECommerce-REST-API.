
package com.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.entity.Payment;
import com.ecommerce.entity.Order;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    List<Payment> findByOrder(Order order);

}

