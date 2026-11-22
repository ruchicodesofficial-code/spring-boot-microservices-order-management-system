package com.ruchicodes.notificationservice.dto;

import com.ruchicodes.notificationservice.enums.NotificationStatus;
import com.ruchicodes.notificationservice.enums.NotificationType;

import java.time.LocalDateTime;

public record NotificationResponse(

        Long id,
        Long userId,
        String  title,
        String message,
        NotificationType type,
        NotificationStatus status,
        LocalDateTime createdAt,
        LocalDateTime readAt
) {
}
