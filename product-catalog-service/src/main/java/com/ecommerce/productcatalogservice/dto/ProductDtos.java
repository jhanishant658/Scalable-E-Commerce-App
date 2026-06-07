package com.ecommerce.productcatalogservice.dto;

import java.math.BigDecimal;

public class ProductDtos {
    public record CategoryRequest(String name, String description) {}
    public record CategoryResponse(Long id, String name, String description) {}
    public record ProductRequest(String name, String description, BigDecimal price, Integer inventoryQuantity, String imageUrl, Long categoryId) {}
    public record ProductResponse(Long id, String name, String description, BigDecimal price, Integer inventoryQuantity, String imageUrl, boolean active, CategoryResponse category) {}
    public record InventoryRequest(Integer quantity) {}
    public record AvailabilityResponse(Long productId, boolean available, Integer inventoryQuantity, BigDecimal price) {}
}
