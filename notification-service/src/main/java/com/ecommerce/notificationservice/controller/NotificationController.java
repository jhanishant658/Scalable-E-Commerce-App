package com.ecommerce.notificationservice.controller;

import com.ecommerce.notificationservice.dto.NotificationDtos;
import com.ecommerce.notificationservice.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;

    @PostMapping("/api/v1/notifications")
    public NotificationDtos.NotificationResponse send(@RequestBody NotificationDtos.NotificationRequest request) {
        return notificationService.send(request);
    }

    @GetMapping("/api/v1/notifications/users/{userId}")
    public List<NotificationDtos.NotificationResponse> userNotifications(@PathVariable Long userId) {
        return notificationService.userNotifications(userId);
    }
}
