package com.ecommerce.orderservice.client;

import com.ecommerce.orderservice.dto.OrderDtos;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "payment-service")
public interface PaymentClient {
    @PostMapping("/api/v1/payments")
    OrderDtos.PaymentResponse pay(@RequestBody OrderDtos.PaymentRequest request);
}
