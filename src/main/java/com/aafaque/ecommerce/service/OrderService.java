package com.aafaque.ecommerce.service;

import com.aafaque.ecommerce.model.*;
import com.aafaque.ecommerce.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public Order placeOrder(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) return null;

        Cart cart = cartRepository.findByUser(user);
        if (cart == null || cart.getItems().isEmpty()) return null;

        // Calculate total
        double total = 0;
        for (CartItem item : cart.getItems()) {
            total += item.getProduct().getPrice() * item.getQuantity();
        }

        // Create order
        Order order = new Order();
        order.setUser(user);
        order.setTotalAmount(total);
        order.setStatus("PENDING");
        order.setOrderDate(LocalDateTime.now());
        order = orderRepository.save(order);

        // Create order items from cart items
        List<OrderItem> orderItems = new ArrayList<>();
        for (CartItem cartItem : cart.getItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(cartItem.getProduct().getPrice());
            orderItems.add(orderItem);
        }
        order.setItems(orderItems);
        order = orderRepository.save(order);

        // Clear the cart
        cart.getItems().clear();
        cartRepository.save(cart);

        return order;
    }

    public List<Order> getOrderHistory(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) return null;
        return orderRepository.findByUser(user);
    }

    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId).orElse(null);
    }
}