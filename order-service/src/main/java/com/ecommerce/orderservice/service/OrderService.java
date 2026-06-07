package com.ecommerce.orderservice.service;

import com.ecommerce.orderservice.dto.OrderDtos;
import com.ecommerce.orderservice.entity.CustomerOrder;
import com.ecommerce.orderservice.entity.OrderItem;
import com.ecommerce.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderRemoteService remoteService;

    @Transactional
    public OrderDtos.OrderResponse placeOrder(OrderDtos.PlaceOrderRequest request) {
        OrderDtos.CartResponse cart = remoteService.fetchCart(request.userId());
        if (cart.items().isEmpty()) {
            throw new IllegalArgumentException("Cart is empty");
        }
        CustomerOrder order = CustomerOrder.builder()
                .userId(request.userId())
                .totalAmount(cart.total())
                .status(CustomerOrder.OrderStatus.CREATED)
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();
        cart.items().forEach(item -> order.getItems().add(OrderItem.builder()
                .order(order)
                .productId(item.productId())
                .productName(item.productName())
                .unitPrice(item.unitPrice())
                .quantity(item.quantity())
                .lineTotal(item.lineTotal())
                .build()));
        CustomerOrder saved = orderRepository.save(order);

        OrderDtos.PaymentResponse payment = remoteService.pay(new OrderDtos.PaymentRequest(saved.getId(), request.userId(), saved.getTotalAmount()));
        if ("SUCCESS".equals(payment.status())) {
            cart.items().forEach(item -> remoteService.reduceInventory(item.productId(), item.quantity()));
            remoteService.clearCart(request.userId());
            saved.setStatus(CustomerOrder.OrderStatus.PAID);
            remoteService.notifyUser(request.userId(), request.notificationRecipient(), "Order confirmed",
                    "Your order #" + saved.getId() + " has been placed successfully.");
        } else {
            saved.setStatus(CustomerOrder.OrderStatus.PAYMENT_FAILED);
        }
        saved.setUpdatedAt(Instant.now());
        return toResponse(orderRepository.save(saved));
    }

    public List<OrderDtos.OrderResponse> history(Long userId) {
        return orderRepository.findByUserIdOrderByCreatedAtDesc(userId).stream().map(this::toResponse).toList();
    }

    public OrderDtos.OrderResponse order(Long orderId) {
        return orderRepository.findById(orderId).map(this::toResponse)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));
    }

    @Transactional
    public OrderDtos.OrderResponse updateStatus(Long orderId, OrderDtos.UpdateStatusRequest request) {
        CustomerOrder order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));
        order.setStatus(request.status());
        order.setUpdatedAt(Instant.now());
        return toResponse(orderRepository.save(order));
    }

    private OrderDtos.OrderResponse toResponse(CustomerOrder order) {
        List<OrderDtos.OrderItemResponse> items = order.getItems().stream()
                .map(item -> new OrderDtos.OrderItemResponse(item.getProductId(), item.getProductName(),
                        item.getUnitPrice(), item.getQuantity(), item.getLineTotal()))
                .toList();
        return new OrderDtos.OrderResponse(order.getId(), order.getUserId(), order.getTotalAmount(),
                order.getStatus(), order.getCreatedAt(), items);
    }
}
