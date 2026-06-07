package com.ecommerce.cartservice.dto;

import java.math.BigDecimal;
import java.util.List;

public class CartDtos {
    public record AddItemRequest(Long productId, Integer quantity) {}
    public record UpdateQuantityRequest(Integer quantity) {}
    public record ProductResponse(Long id, String name, String description, BigDecimal price, Integer inventoryQuantity, String imageUrl, boolean active, Object category) {}
    public record AvailabilityResponse(Long productId, boolean available, Integer inventoryQuantity, BigDecimal price) {}
    public record CartItemResponse(Long id, Long productId, String productName, BigDecimal unitPrice, Integer quantity, BigDecimal lineTotal) {}
    public record CartResponse(Long userId, List<CartItemResponse> items, BigDecimal total) {}
}
