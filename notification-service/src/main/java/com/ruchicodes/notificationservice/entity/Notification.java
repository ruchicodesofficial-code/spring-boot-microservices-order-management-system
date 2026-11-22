package com.ruchicodes.notificationservice.entity;

import com.ruchicodes.notificationservice.enums.NotificationStatus;
import com.ruchicodes.notificationservice.enums.NotificationType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false,length = 150)
    private String title;

    @Column(nullable = false,length = 1000)
    private String message;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false,length = 50)
    private NotificationType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false,length = 20)
    private NotificationStatus status;

    @Column(nullable = false,updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime readAt;

    @PrePersist
    protected void onCreate(){
        createdAt=LocalDateTime.now();
    if (status==null){
    status= NotificationStatus.UNREAD;
    }
    }
}
