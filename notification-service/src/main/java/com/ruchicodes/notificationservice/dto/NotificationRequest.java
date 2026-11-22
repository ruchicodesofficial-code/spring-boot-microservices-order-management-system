package com.ruchicodes.notificationservice.dto;

import com.ruchicodes.notificationservice.enums.NotificationType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record NotificationRequest(
        @NotNull(message = "User ID is required")
        Long userId,

        @NotBlank(message = "Title is required")
        @Size(max = 150)
        String title,

        @NotBlank(message = "Message is required")
        @Size(max = 1000)
        String message,

        @NotNull(message = "Notification type is required")
        NotificationType type
) {

}
