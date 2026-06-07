package com.ecommerce.cartservice.client;

import com.ecommerce.cartservice.dto.CartDtos;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "product-catalog-service")
public interface ProductClient {
    @GetMapping("/api/v1/products/{productId}")
    CartDtos.ProductResponse product(@PathVariable Long productId);

    @GetMapping("/api/v1/products/{productId}/availability")
    CartDtos.AvailabilityResponse availability(@PathVariable Long productId, @RequestParam Integer quantity);
}
