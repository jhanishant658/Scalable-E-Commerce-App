package com.ecommerce.cartservice.service;

import com.ecommerce.cartservice.client.ProductClient;
import com.ecommerce.cartservice.dto.CartDtos;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class ProductLookupService {
    private final ProductClient productClient;

    @CircuitBreaker(name = "productService", fallbackMethod = "availabilityFallback")
    public CartDtos.AvailabilityResponse checkAvailability(Long productId, Integer quantity) {
        return productClient.availability(productId, quantity);
    }

    @CircuitBreaker(name = "productService", fallbackMethod = "productFallback")
    public CartDtos.ProductResponse loadProduct(Long productId) {
        return productClient.product(productId);
    }

    public CartDtos.AvailabilityResponse availabilityFallback(Long productId, Integer quantity, Throwable ex) {
        return new CartDtos.AvailabilityResponse(productId, false, 0, BigDecimal.ZERO);
    }

    public CartDtos.ProductResponse productFallback(Long productId, Throwable ex) {
        throw new IllegalArgumentException("Product service is unavailable");
    }
}
