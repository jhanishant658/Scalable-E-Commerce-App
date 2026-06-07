package com.ecommerce.notificationservice.dto;

import com.ecommerce.notificationservice.entity.Notification;

import java.time.Instant;

public class NotificationDtos {
    public record NotificationRequest(Long userId, String recipient, Notification.Channel channel, String subject, String message) {}
    public record NotificationResponse(Long id, Long userId, String recipient, Notification.Channel channel, String subject, String message, Instant sentAt) {}
}
