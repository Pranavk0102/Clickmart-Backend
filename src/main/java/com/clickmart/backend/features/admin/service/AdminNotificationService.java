package com.clickmart.backend.features.admin.service;

import com.clickmart.backend.config.SecurityUtils;
import com.clickmart.backend.enums.OrderStatus;
import com.clickmart.backend.enums.TicketStatus;
import com.clickmart.backend.features.admin.dto.AdminNotificationSummaryDTO;
import com.clickmart.backend.features.notification.service.NotificationService;
import com.clickmart.backend.features.order.repository.OrderRepository;
import com.clickmart.backend.features.support.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class AdminNotificationService {

    private final TicketRepository ticketRepository;
    private final OrderRepository orderRepository;
    private final NotificationService notificationService;
    private final SecurityUtils securityUtils;

    @Autowired
    public AdminNotificationService(TicketRepository ticketRepository,
                               OrderRepository orderRepository,
                               NotificationService notificationService,
                               SecurityUtils securityUtils) {
        this.ticketRepository = ticketRepository;
        this.orderRepository = orderRepository;
        this.notificationService = notificationService;
        this.securityUtils = securityUtils;
    }

    public AdminNotificationSummaryDTO getSummary() {
        AdminNotificationSummaryDTO dto = new AdminNotificationSummaryDTO();
        dto.setOpenTickets(ticketRepository.countByStatus(TicketStatus.OPEN));
        dto.setInProgressTickets(ticketRepository.countByStatus(TicketStatus.IN_PROGRESS));
        dto.setPendingOrders(orderRepository.countByStatus(OrderStatus.PENDING));
        dto.setProcessingOrders(orderRepository.countByStatus(OrderStatus.PROCESSING));
        dto.setUnreadNotifications(notificationService.getUnreadCountForUser(securityUtils.getCurrentUserId()));
        return dto;
    }
}
