package com.clickmart.backend.features.dashboard.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.clickmart.backend.enums.OrderStatus;
import com.clickmart.backend.enums.Role;
import com.clickmart.backend.enums.TicketStatus;
import com.clickmart.backend.features.auth.repository.UserRepository;
import com.clickmart.backend.features.order.repository.OrderRepository;
import com.clickmart.backend.features.product.repository.ProductRepository;
import com.clickmart.backend.features.support.repository.TicketRepository;

@Service
@Transactional(readOnly = true)
public class DashboardService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final TicketRepository ticketRepository;

    @Autowired
    public DashboardService(OrderRepository orderRepository,
                               UserRepository userRepository,
                               ProductRepository productRepository,
                               TicketRepository ticketRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.ticketRepository = ticketRepository;
    }

    public Map<String, Object> getStats() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalOrders", orderRepository.count());
        stats.put("pendingOrders", orderRepository.countByStatus(OrderStatus.PENDING));
        stats.put("processingOrders", orderRepository.countByStatus(OrderStatus.PROCESSING));
        stats.put("deliveredOrders", orderRepository.countByStatus(OrderStatus.DELIVERED));
        stats.put("cancelledOrders", orderRepository.countByStatus(OrderStatus.CANCELLED));
        double totalRevenue = orderRepository.sumTotalRevenue() != null ? orderRepository.sumTotalRevenue() : 0.0;
        stats.put("totalRevenue", totalRevenue);
        stats.put("totalCustomers", userRepository.countByRole(Role.CUSTOMER));
        stats.put("totalProducts", productRepository.countByActiveTrue());
        stats.put("openTickets", ticketRepository.countByStatus(TicketStatus.OPEN));
        stats.put("inProgressTickets", ticketRepository.countByStatus(TicketStatus.IN_PROGRESS));
        return stats;
    }
}
