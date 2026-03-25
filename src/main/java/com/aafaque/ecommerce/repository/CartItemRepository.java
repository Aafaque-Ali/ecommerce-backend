package com.aafaque.ecommerce.repository;

import com.aafaque.ecommerce.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}