package com.clickmart.backend.features.notification.controller;

import com.clickmart.backend.dto.ApiResponse;
import com.clickmart.backend.features.notification.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    @Autowired
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    
    @GetMapping
    public ResponseEntity<ApiResponse<List<Map<String, Object>>>> getNotifications() {
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(true, "Notifications fetched",
                notificationService.getMyNotifications()));
    }

    
    @GetMapping("/unread-count")
    public ResponseEntity<ApiResponse<Long>> getUnreadCount() {
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(true, "Unread count",
                notificationService.getUnreadCount()));
    }

    
    @PatchMapping("/mark-all-read")
    public ResponseEntity<ApiResponse<Void>> markAllRead() {
        notificationService.markAllRead();
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(true, "All notifications marked as read", null));
    }

    
    @PatchMapping("/{id}/read")
    public ResponseEntity<ApiResponse<Void>> markRead(@PathVariable("id") Long id) {
        notificationService.markRead(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(true, "Notification marked as read", null));
    }
}
