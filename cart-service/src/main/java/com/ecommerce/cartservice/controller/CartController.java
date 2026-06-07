package com.ecommerce.cartservice.controller;

import com.ecommerce.cartservice.dto.CartDtos;
import com.ecommerce.cartservice.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @GetMapping("/api/v1/carts/{userId}")
    public CartDtos.CartResponse cart(@PathVariable Long userId) {
        return cartService.getCart(userId);
    }

    @PostMapping("/api/v1/carts/{userId}/items")
    public CartDtos.CartResponse addItem(@PathVariable Long userId, @RequestBody CartDtos.AddItemRequest request) {
        return cartService.addItem(userId, request);
    }

    @PutMapping("/api/v1/carts/{userId}/items/{productId}")
    public CartDtos.CartResponse updateQuantity(@PathVariable Long userId, @PathVariable Long productId, @RequestBody CartDtos.UpdateQuantityRequest request) {
        return cartService.updateQuantity(userId, productId, request);
    }

    @DeleteMapping("/api/v1/carts/{userId}/items/{productId}")
    public CartDtos.CartResponse removeItem(@PathVariable Long userId, @PathVariable Long productId) {
        return cartService.removeItem(userId, productId);
    }

    @DeleteMapping("/api/v1/carts/{userId}")
    public void clearCart(@PathVariable Long userId) {
        cartService.clearCart(userId);
    }
}
