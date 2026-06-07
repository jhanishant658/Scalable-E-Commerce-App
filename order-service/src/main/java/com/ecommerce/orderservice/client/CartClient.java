package com.ecommerce.orderservice.client;

import com.ecommerce.orderservice.dto.OrderDtos;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "cart-service")
public interface CartClient {
    @GetMapping("/api/v1/carts/{userId}")
    OrderDtos.CartResponse cart(@PathVariable Long userId);

    @DeleteMapping("/api/v1/carts/{userId}")
    void clearCart(@PathVariable Long userId);
}
