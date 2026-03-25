package com.aafaque.ecommerce.controller;

import com.aafaque.ecommerce.model.Order;
import com.aafaque.ecommerce.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/place/{userId}")
    public ResponseEntity<?> placeOrder(@PathVariable Long userId) {
        Order order = orderService.placeOrder(userId);
        if (order == null) {
            return ResponseEntity.status(400).body("Cart is empty or user not found");
        }
        return ResponseEntity.ok(order);
    }

    @GetMapping("/history/{userId}")
    public ResponseEntity<?> getOrderHistory(@PathVariable Long userId) {
        List<Order> orders = orderService.getOrderHistory(userId);
        if (orders == null) {
            return ResponseEntity.status(404).body("User not found");
        }
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<?> getOrderById(@PathVariable Long orderId) {
        Order order = orderService.getOrderById(orderId);
        if (order == null) {
            return ResponseEntity.status(404).body("Order not found");
        }
        return ResponseEntity.ok(order);
    }
}