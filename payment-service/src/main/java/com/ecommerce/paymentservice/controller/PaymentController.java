package com.ecommerce.paymentservice.controller;

import com.ecommerce.paymentservice.dto.PaymentDtos;
import com.ecommerce.paymentservice.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping("/api/v1/wallets")
    public PaymentDtos.WalletResponse createWallet(@RequestBody PaymentDtos.WalletRequest request) {
        return paymentService.createWallet(request);
    }

    @GetMapping("/api/v1/wallets/{userId}")
    public PaymentDtos.WalletResponse wallet(@PathVariable Long userId) {
        return paymentService.wallet(userId);
    }

    @PostMapping("/api/v1/wallets/{userId}/top-up")
    public PaymentDtos.WalletResponse topUp(@PathVariable Long userId, @RequestBody PaymentDtos.TopUpRequest request) {
        return paymentService.topUp(userId, request);
    }

    @PostMapping("/api/v1/payments")
    public PaymentDtos.PaymentResponse pay(@RequestBody PaymentDtos.PaymentRequest request) {
        return paymentService.pay(request);
    }

    @GetMapping("/api/v1/payments/users/{userId}")
    public List<PaymentDtos.PaymentResponse> payments(@PathVariable Long userId) {
        return paymentService.payments(userId);
    }
}
