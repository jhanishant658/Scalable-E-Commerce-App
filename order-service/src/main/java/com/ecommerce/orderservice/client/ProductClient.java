package com.ecommerce.orderservice.client;

import com.ecommerce.orderservice.dto.OrderDtos;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "product-catalog-service")
public interface ProductClient {
    @PostMapping("/api/v1/products/{productId}/inventory/reduce")
    void reduceInventory(@PathVariable Long productId, @RequestBody OrderDtos.InventoryRequest request);
}
