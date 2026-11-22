package com.ruchicodes.notificationservice.controller;

import com.ruchicodes.notificationservice.dto.NotificationRequest;
import com.ruchicodes.notificationservice.dto.NotificationResponse;
import com.ruchicodes.notificationservice.service.NotificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping
    public NotificationResponse createNotificationRequest(@Valid @RequestBody NotificationRequest request){
        return notificationService.createNotification(request);
    }

    @GetMapping("/user/{userId}")
    public List<NotificationResponse> getNotificationByUserId(@PathVariable Long userId){
      return notificationService.getNotificationByUserId(userId);
    }

    @GetMapping("/user/{userId}/unread")
    public List<NotificationResponse> getUnreadNotifications(@PathVariable Long userId){
        return notificationService.getUnreadNotifications(userId);
    }

    @PatchMapping("/{id}/read")
    public NotificationResponse markAsRead(@PathVariable Long id){
        return notificationService.markAsRead(id);
    }
}
