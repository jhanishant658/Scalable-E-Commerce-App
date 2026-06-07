package com.ecommerce.productcatalogservice.controller;

import com.ecommerce.productcatalogservice.dto.ProductDtos;
import com.ecommerce.productcatalogservice.service.ProductCatalogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductCatalogController {
    private final ProductCatalogService service;

    @PostMapping("/api/v1/categories")
    public ResponseEntity<ProductDtos.CategoryResponse> createCategory(@RequestBody ProductDtos.CategoryRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createCategory(request));
    }

    @GetMapping("/api/v1/categories")
    public List<ProductDtos.CategoryResponse> categories() {
        return service.categories();
    }

    @PostMapping("/api/v1/products")
    public ResponseEntity<ProductDtos.ProductResponse> createProduct(@RequestBody ProductDtos.ProductRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createProduct(request));
    }

    @GetMapping("/api/v1/products")
    public List<ProductDtos.ProductResponse> products(@RequestParam(required = false) Long categoryId) {
        return service.products(categoryId);
    }

    @GetMapping("/api/v1/products/{productId}")
    public ProductDtos.ProductResponse product(@PathVariable Long productId) {
        return service.product(productId);
    }

    @PutMapping("/api/v1/products/{productId}/inventory")
    public ProductDtos.ProductResponse updateInventory(@PathVariable Long productId, @RequestBody ProductDtos.InventoryRequest request) {
        return service.updateInventory(productId, request);
    }

    @PostMapping("/api/v1/products/{productId}/inventory/reduce")
    public ProductDtos.ProductResponse reduceInventory(@PathVariable Long productId, @RequestBody ProductDtos.InventoryRequest request) {
        return service.reduceInventory(productId, request);
    }

    @GetMapping("/api/v1/products/{productId}/availability")
    public ProductDtos.AvailabilityResponse availability(@PathVariable Long productId, @RequestParam Integer quantity) {
        return service.availability(productId, quantity);
    }
}
