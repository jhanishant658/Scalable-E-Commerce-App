package com.ecommerce.paymentservice.dto;

import com.ecommerce.paymentservice.entity.Payment;

import java.math.BigDecimal;
import java.time.Instant;

public class PaymentDtos {
    public record WalletRequest(Long userId) {}
    public record TopUpRequest(BigDecimal amount) {}
    public record PaymentRequest(Long orderId, Long userId, BigDecimal amount) {}
    public record WalletResponse(Long id, Long userId, BigDecimal balance) {}
    public record PaymentResponse(Long id, Long orderId, Long userId, BigDecimal amount, Payment.PaymentStatus status, String message, Instant createdAt) {}
}
