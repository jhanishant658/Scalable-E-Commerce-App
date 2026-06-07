package com.ecommerce.cartservice.service;

import com.ecommerce.cartservice.dto.CartDtos;
import com.ecommerce.cartservice.entity.CartItem;
import com.ecommerce.cartservice.repository.CartItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartItemRepository cartItemRepository;
    private final ProductLookupService productLookupService;

    @Transactional
    public CartDtos.CartResponse addItem(Long userId, CartDtos.AddItemRequest request) {
        CartDtos.AvailabilityResponse availability = productLookupService.checkAvailability(request.productId(), request.quantity());
        if (!availability.available()) {
            throw new IllegalArgumentException("Product is not available in requested quantity");
        }
        CartDtos.ProductResponse product = productLookupService.loadProduct(request.productId());
        CartItem item = cartItemRepository.findByUserIdAndProductId(userId, request.productId())
                .orElse(CartItem.builder().userId(userId).productId(request.productId()).quantity(0).build());
        item.setProductName(product.name());
        item.setUnitPrice(product.price());
        item.setQuantity(item.getQuantity() + request.quantity());
        cartItemRepository.save(item);
        return getCart(userId);
    }

    @Transactional
    public CartDtos.CartResponse updateQuantity(Long userId, Long productId, CartDtos.UpdateQuantityRequest request) {
        CartItem item = cartItemRepository.findByUserIdAndProductId(userId, productId)
                .orElseThrow(() -> new IllegalArgumentException("Cart item not found"));
        if (request.quantity() <= 0) {
            cartItemRepository.delete(item);
        } else {
            CartDtos.AvailabilityResponse availability = productLookupService.checkAvailability(productId, request.quantity());
            if (!availability.available()) {
                throw new IllegalArgumentException("Product is not available in requested quantity");
            }
            item.setQuantity(request.quantity());
            cartItemRepository.save(item);
        }
        return getCart(userId);
    }

    @Transactional
    public CartDtos.CartResponse removeItem(Long userId, Long productId) {
        cartItemRepository.findByUserIdAndProductId(userId, productId).ifPresent(cartItemRepository::delete);
        return getCart(userId);
    }

    @Transactional
    public void clearCart(Long userId) {
        cartItemRepository.deleteByUserId(userId);
    }

    public CartDtos.CartResponse getCart(Long userId) {
        List<CartDtos.CartItemResponse> items = cartItemRepository.findByUserId(userId).stream()
                .map(this::toResponse)
                .toList();
        BigDecimal total = items.stream().map(CartDtos.CartItemResponse::lineTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return new CartDtos.CartResponse(userId, items, total);
    }

    private CartDtos.CartItemResponse toResponse(CartItem item) {
        BigDecimal lineTotal = item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
        return new CartDtos.CartItemResponse(item.getId(), item.getProductId(), item.getProductName(),
                item.getUnitPrice(), item.getQuantity(), lineTotal);
    }
}
