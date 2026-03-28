package com.aafaque.ecommerce.controller;

import com.aafaque.ecommerce.model.Cart;
import com.aafaque.ecommerce.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<?> addToCart(
            @RequestParam Long userId,
            @RequestParam Long productId,
            @RequestParam Integer quantity) {
        Cart cart = cartService.addToCart(userId, productId, quantity);
        if (cart == null) {
            return ResponseEntity.status(404).body("User or Product not found");
        }
        return ResponseEntity.ok(cart);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> viewCart(@PathVariable Long userId) {
        Cart cart = cartService.viewCart(userId);
        if (cart == null) {
            return ResponseEntity.status(404).body("User not found");
        }
        return ResponseEntity.ok(cart);
    }

    @DeleteMapping("/remove/{cartItemId}")
    public ResponseEntity<?> removeFromCart(
            @PathVariable Long cartItemId,
            @RequestParam Long userId) {
        cartService.removeFromCart(cartItemId, userId);
        return ResponseEntity.ok("Item removed from cart");
    }
    
    @PutMapping("/update/{cartItemId}")
    public ResponseEntity<?> updateQuantity(
            @PathVariable Long cartItemId,
            @RequestParam Integer quantity) {
        cartService.updateQuantity(cartItemId, quantity);
        return ResponseEntity.ok("Quantity updated");
    }
}