package com.aafaque.ecommerce.repository;

import com.aafaque.ecommerce.model.Cart;
import com.aafaque.ecommerce.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByUser(User user);
}