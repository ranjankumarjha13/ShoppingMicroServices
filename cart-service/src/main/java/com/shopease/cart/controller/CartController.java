package com.shopease.cart.controller;

import com.shopease.cart.model.Cart;
import com.shopease.cart.model.CartItem;
import com.shopease.cart.service.CartService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Cart> getCart(
            @PathVariable String userId) {

        return ResponseEntity.ok(
                cartService.getCart(userId)
        );
    }

    @PostMapping("/{userId}/items")
    public ResponseEntity<Cart> addToCart(
            @PathVariable String userId,
            @RequestBody CartItem item) {

        return ResponseEntity.ok(
                cartService.addToCart(
                        userId,
                        item
                )
        );
    }

    @PutMapping("/{userId}/items/{productId}")
    public ResponseEntity<Cart> updateQuantity(
            @PathVariable String userId,
            @PathVariable String productId,
            @RequestParam int quantity) {

        return ResponseEntity.ok(
                cartService.updateQuantity(
                        userId,
                        productId,
                        quantity
                )
        );
    }

    @DeleteMapping("/{userId}/items/{productId}")
    public ResponseEntity<Cart> removeFromCart(
            @PathVariable String userId,
            @PathVariable String productId) {

        return ResponseEntity.ok(
                cartService.removeFromCart(
                        userId,
                        productId
                )
        );
    }
}
