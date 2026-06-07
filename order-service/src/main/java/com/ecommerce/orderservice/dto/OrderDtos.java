package com.ecommerce.orderservice.dto;

import com.ecommerce.orderservice.entity.CustomerOrder;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public class OrderDtos {
    public record PlaceOrderRequest(Long userId, String notificationRecipient) {}
    public record UpdateStatusRequest(CustomerOrder.OrderStatus status) {}
    public record InventoryRequest(Integer quantity) {}
    public record PaymentRequest(Long orderId, Long userId, BigDecimal amount) {}
    public record NotificationRequest(Long userId, String recipient, String channel, String subject, String message) {}
    public record CartItemResponse(Long id, Long productId, String productName, BigDecimal unitPrice, Integer quantity, BigDecimal lineTotal) {}
    public record CartResponse(Long userId, List<CartItemResponse> items, BigDecimal total) {}
    public record PaymentResponse(Long id, Long orderId, Long userId, BigDecimal amount, String status, String message, Instant createdAt) {}
    public record OrderItemResponse(Long productId, String productName, BigDecimal unitPrice, Integer quantity, BigDecimal lineTotal) {}
    public record OrderResponse(Long id, Long userId, BigDecimal totalAmount, CustomerOrder.OrderStatus status, Instant createdAt, List<OrderItemResponse> items) {}
}
