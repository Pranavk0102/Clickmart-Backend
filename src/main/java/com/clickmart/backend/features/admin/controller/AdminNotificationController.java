package com.clickmart.backend.features.admin.controller;

import com.clickmart.backend.dto.ApiResponse;
import com.clickmart.backend.features.admin.dto.AdminNotificationSummaryDTO;
import com.clickmart.backend.features.admin.service.AdminNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/admin/notifications")
public class AdminNotificationController {

    private final AdminNotificationService adminNotificationService;

    @Autowired
    public AdminNotificationController(AdminNotificationService adminNotificationService) {
        this.adminNotificationService = adminNotificationService;
    }

    @GetMapping("/summary")
    public ResponseEntity<ApiResponse<AdminNotificationSummaryDTO>> getSummary() {
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(true, "Notification summary",
                adminNotificationService.getSummary()));
    }
}
