package com.ecommerce.orderservice.service;

import com.ecommerce.orderservice.client.CartClient;
import com.ecommerce.orderservice.client.NotificationClient;
import com.ecommerce.orderservice.client.PaymentClient;
import com.ecommerce.orderservice.client.ProductClient;
import com.ecommerce.orderservice.dto.OrderDtos;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderRemoteService {
    private final CartClient cartClient;
    private final PaymentClient paymentClient;
    private final ProductClient productClient;
    private final NotificationClient notificationClient;

    @CircuitBreaker(name = "cartService", fallbackMethod = "cartFallback")
    public OrderDtos.CartResponse fetchCart(Long userId) {
        return cartClient.cart(userId);
    }

    @CircuitBreaker(name = "paymentService", fallbackMethod = "paymentFallback")
    public OrderDtos.PaymentResponse pay(OrderDtos.PaymentRequest request) {
        return paymentClient.pay(request);
    }

    @CircuitBreaker(name = "productService", fallbackMethod = "inventoryFallback")
    public void reduceInventory(Long productId, Integer quantity) {
        productClient.reduceInventory(productId, new OrderDtos.InventoryRequest(quantity));
    }

    @CircuitBreaker(name = "cartService", fallbackMethod = "clearCartFallback")
    public void clearCart(Long userId) {
        cartClient.clearCart(userId);
    }

    @CircuitBreaker(name = "notificationService", fallbackMethod = "notificationFallback")
    public void notifyUser(Long userId, String recipient, String subject, String message) {
        notificationClient.send(new OrderDtos.NotificationRequest(userId, recipient, "EMAIL", subject, message));
    }

    public OrderDtos.CartResponse cartFallback(Long userId, Throwable ex) {
        throw new IllegalArgumentException("Cart service is unavailable");
    }

    public OrderDtos.PaymentResponse paymentFallback(OrderDtos.PaymentRequest request, Throwable ex) {
        throw new IllegalArgumentException("Payment service is unavailable");
    }

    public void inventoryFallback(Long productId, Integer quantity, Throwable ex) {
        throw new IllegalArgumentException("Product service is unavailable");
    }

    public void clearCartFallback(Long userId, Throwable ex) {
    }

    public void notificationFallback(Long userId, String recipient, String subject, String message, Throwable ex) {
    }
}
