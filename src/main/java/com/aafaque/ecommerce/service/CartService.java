package com.aafaque.ecommerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aafaque.ecommerce.model.Cart;
import com.aafaque.ecommerce.model.CartItem;
import com.aafaque.ecommerce.model.Product;
import com.aafaque.ecommerce.model.User;
import com.aafaque.ecommerce.repository.CartItemRepository;
import com.aafaque.ecommerce.repository.CartRepository;
import com.aafaque.ecommerce.repository.ProductRepository;
import com.aafaque.ecommerce.repository.UserRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @PersistenceContext
    private EntityManager entityManager;
    
    public Cart getOrCreateCart(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) return null;

        Cart cart = cartRepository.findByUser(user);
        if (cart == null) {
            cart = new Cart();
            cart.setUser(user);
            cart = cartRepository.save(cart);
        }
        return cart;
    }

    public Cart addToCart(Long userId, Long productId, Integer quantity) {
        Cart cart = getOrCreateCart(userId);
        if (cart == null) return null;

        Product product = productRepository.findById(productId).orElse(null);
        if (product == null) return null;

        // Check if product already in cart
        for (CartItem item : cart.getItems()) {
            if (item.getProduct().getId().equals(productId)) {
                item.setQuantity(item.getQuantity() + quantity);
                cartItemRepository.save(item);
                return cartRepository.findById(cart.getId()).orElse(null);
            }
        }

        // Add new item
        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setProduct(product);
        cartItem.setQuantity(quantity);
        cartItemRepository.save(cartItem);

        return cartRepository.findById(cart.getId()).orElse(null);
    }

    public Cart viewCart(Long userId) {
        return getOrCreateCart(userId);
    }

    @Transactional
    public void removeFromCart(Long cartItemId, Long userId) {
        Cart cart = getOrCreateCart(userId);
        if (cart == null) return;
        
        cart.getItems().removeIf(item -> item.getId().equals(cartItemId));
        cartRepository.save(cart);
    }
    
}