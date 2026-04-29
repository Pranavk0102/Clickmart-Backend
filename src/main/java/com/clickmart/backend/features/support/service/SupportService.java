package com.clickmart.backend.features.support.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.clickmart.backend.config.SecurityUtils;
import com.clickmart.backend.entity.Ticket;
import com.clickmart.backend.entity.User;
import com.clickmart.backend.enums.Role;
import com.clickmart.backend.enums.TicketPriority;
import com.clickmart.backend.enums.TicketStatus;
import com.clickmart.backend.exceptions.ResourceNotFoundException;
import com.clickmart.backend.features.notification.service.NotificationService;
import com.clickmart.backend.features.support.dto.TicketDTO;
import com.clickmart.backend.features.support.dto.TicketRequest;
import com.clickmart.backend.features.support.dto.TicketResponseRequest;
import com.clickmart.backend.features.support.repository.TicketRepository;

@Service
@Transactional
public class SupportService {

    private final TicketRepository ticketRepository;
    private final SecurityUtils securityUtils;
    private final NotificationService notificationService;

    @Autowired
    public SupportService(TicketRepository ticketRepository,
                               SecurityUtils securityUtils,
                               NotificationService notificationService) {
        this.ticketRepository = ticketRepository;
        this.securityUtils = securityUtils;
        this.notificationService = notificationService;
    }

    public TicketDTO submitTicket(TicketRequest req) {
        User user = securityUtils.getCurrentUser();

        Ticket ticket = new Ticket();
        ticket.setUser(user);
        ticket.setTicketNumber("TKT-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        ticket.setSubject(req.getSubject());
        ticket.setCategory(req.getCategory());
        ticket.setMessage(req.getMessage());
        ticket.setRelatedOrderNumber(req.getRelatedOrderNumber());
        ticket.setStatus(TicketStatus.OPEN);

        try {
            ticket.setPriority(TicketPriority.valueOf(req.getPriority().toUpperCase()));
        } catch (IllegalArgumentException | NullPointerException ex) {
            ticket.setPriority(TicketPriority.MEDIUM);
        }

        Ticket saved = ticketRepository.save(ticket);
        notificationService.createNotification(user,
                "Support ticket " + saved.getTicketNumber() + " has been submitted.",
                "SUPPORT");
        notificationService.notifyUsersByRole(Role.ADMIN,
                "New support ticket " + saved.getTicketNumber() + " requires attention.",
                "SUPPORT");
        return toDTO(saved);
    }

    @Transactional(readOnly = true)
    public List<TicketDTO> getMyTickets() {
        Long userId = securityUtils.getCurrentUserId();
        List<Ticket> tickets = ticketRepository.findByUserIdOrderByCreatedAtDesc(userId);
        List<TicketDTO> result = new ArrayList<>();
        for (Ticket ticket : tickets) {
            result.add(toDTO(ticket));
        }
        return result;
    }

    @Transactional(readOnly = true)
    public Page<TicketDTO> getAllTickets(String query, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Ticket> tickets = (query != null && !query.isBlank())
                ? ticketRepository.searchTickets(query, pageable)
                : ticketRepository.findAll(pageable);
        return tickets.map(this::toDTO);
    }

    public TicketDTO respondToTicket(Long id, TicketResponseRequest request) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket not found"));

        ticket.setAdminResponse(request.getResponse());
        ticket.setStatus(TicketStatus.RESOLVED);

        Ticket saved = ticketRepository.save(ticket);
        notificationService.createNotification(saved.getUser(),
                "Support ticket " + saved.getTicketNumber() + " has a new admin response.",
                "SUPPORT");
        return toDTO(saved);
    }

    private TicketDTO toDTO(Ticket ticket) {
        TicketDTO dto = new TicketDTO();
        dto.setId(ticket.getId());
        dto.setTicketNumber(ticket.getTicketNumber());
        dto.setSubject(ticket.getSubject());
        dto.setCategory(ticket.getCategory());
        dto.setPriority(ticket.getPriority() != null ? ticket.getPriority().name().toLowerCase() : "medium");
        dto.setRelatedOrderNumber(ticket.getRelatedOrderNumber());
        dto.setMessage(ticket.getMessage());
        dto.setStatus(ticket.getStatus() != null ? ticket.getStatus().name().toLowerCase() : "open");
        dto.setAdminResponse(ticket.getAdminResponse());
        dto.setCreatedAt(ticket.getCreatedAt());
        if (ticket.getUser() != null) {
            dto.setCustomerName(ticket.getUser().getFirstName() + " " + ticket.getUser().getLastName());
            dto.setCustomerEmail(ticket.getUser().getEmail());
        }
        return dto;
    }
}
