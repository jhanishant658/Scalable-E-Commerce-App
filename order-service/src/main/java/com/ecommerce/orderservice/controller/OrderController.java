package com.ecommerce.orderservice.controller;

import com.ecommerce.orderservice.dto.OrderDtos;
import com.ecommerce.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/api/v1/orders")
    public ResponseEntity<OrderDtos.OrderResponse> placeOrder(@RequestBody OrderDtos.PlaceOrderRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.placeOrder(request));
    }

    @GetMapping("/api/v1/orders/{orderId}")
    public OrderDtos.OrderResponse order(@PathVariable Long orderId) {
        return orderService.order(orderId);
    }

    @GetMapping("/api/v1/orders/users/{userId}")
    public List<OrderDtos.OrderResponse> history(@PathVariable Long userId) {
        return orderService.history(userId);
    }

    @PutMapping("/api/v1/orders/{orderId}/status")
    public OrderDtos.OrderResponse updateStatus(@PathVariable Long orderId, @RequestBody OrderDtos.UpdateStatusRequest request) {
        return orderService.updateStatus(orderId, request);
    }
}
