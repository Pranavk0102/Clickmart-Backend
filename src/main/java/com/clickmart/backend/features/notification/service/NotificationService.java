package com.clickmart.backend.features.notification.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.clickmart.backend.config.SecurityUtils;
import com.clickmart.backend.entity.Notification;
import com.clickmart.backend.entity.User;
import com.clickmart.backend.enums.Role;
import com.clickmart.backend.exceptions.ResourceNotFoundException;
import com.clickmart.backend.features.auth.repository.UserRepository;
import com.clickmart.backend.features.notification.repository.NotificationRepository;

@Service
@Transactional
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final SecurityUtils securityUtils;
    private final UserRepository userRepository;

    @Autowired
    public NotificationService(NotificationRepository notificationRepository,
                               SecurityUtils securityUtils,
                               UserRepository userRepository) {
        this.notificationRepository = notificationRepository;
        this.securityUtils = securityUtils;
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public List<Map<String, Object>> getMyNotifications() {
        Long userId = securityUtils.getCurrentUserId();
        List<Notification> notifications = notificationRepository.findByUserIdOrderByCreatedAtDesc(userId);
        List<Map<String, Object>> result = new ArrayList<>();
        for (Notification notification : notifications) {
            result.add(toMap(notification));
        }
        return result;
    }

    @Transactional(readOnly = true)
    public long getUnreadCount() {
        return notificationRepository.countByUserIdAndIsReadFalse(securityUtils.getCurrentUserId());
    }

    public void markAllRead() {
        Long userId = securityUtils.getCurrentUserId();
        notificationRepository.findByUserIdAndIsReadFalse(userId).forEach(notification -> notification.setIsRead(true));
    }

    public void markRead(Long id) {
        Long userId = securityUtils.getCurrentUserId();
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notification not found"));
        if (!notification.getUser().getId().equals(userId)) {
            throw new ResourceNotFoundException("Notification not found");
        }
        notification.setIsRead(true);
    }

    public Notification createNotification(User user, String message, String type) {
        Notification notification = new Notification();
        notification.setUser(user);
        notification.setMessage(message);
        notification.setType(type);
        notification.setIsRead(false);
        return notificationRepository.save(notification);
    }

    public void notifyUsersByRole(Role role, String message, String type) {
        userRepository.findByRole(role).forEach(user -> createNotification(user, message, type));
    }

    @Transactional(readOnly = true)
    public long getUnreadCountForUser(Long userId) {
        return notificationRepository.countByUserIdAndIsReadFalse(userId);
    }

    private Map<String, Object> toMap(Notification notification) {
        return Map.of(
                "id", notification.getId(),
                "message", notification.getMessage(),
                "type", notification.getType(),
                "isRead", Boolean.TRUE.equals(notification.isRead()),
                "createdAt", notification.getCreatedAt());
    }
}
