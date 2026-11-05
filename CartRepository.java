
package com.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.entity.Cart;
import com.ecommerce.entity.User;

public interface CartRepository extends JpaRepository<Cart, Long> {

    List<Cart> findByUser(User user);
}
