package com.ecommerce.notificationservice.service;

import com.ecommerce.notificationservice.dto.NotificationDtos;
import com.ecommerce.notificationservice.entity.Notification;
import com.ecommerce.notificationservice.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {
    private final NotificationRepository notificationRepository;

    public NotificationDtos.NotificationResponse send(NotificationDtos.NotificationRequest request) {
        Notification notification = Notification.builder()
                .userId(request.userId())
                .recipient(request.recipient())
                .channel(request.channel())
                .subject(request.subject())
                .message(request.message())
                .sentAt(Instant.now())
                .build();
        log.info("Sending {} notification to {}: {}", request.channel(), request.recipient(), request.subject());
        return toResponse(notificationRepository.save(notification));
    }

    public List<NotificationDtos.NotificationResponse> userNotifications(Long userId) {
        return notificationRepository.findByUserId(userId).stream().map(this::toResponse).toList();
    }

    private NotificationDtos.NotificationResponse toResponse(Notification notification) {
        return new NotificationDtos.NotificationResponse(notification.getId(), notification.getUserId(),
                notification.getRecipient(), notification.getChannel(), notification.getSubject(),
                notification.getMessage(), notification.getSentAt());
    }
}
