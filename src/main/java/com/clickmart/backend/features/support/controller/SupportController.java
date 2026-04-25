package com.clickmart.backend.features.support.controller;

import com.clickmart.backend.dto.ApiResponse;
import com.clickmart.backend.features.support.dto.TicketDTO;
import com.clickmart.backend.features.support.dto.TicketRequest;
import com.clickmart.backend.features.support.dto.TicketResponseRequest;
import com.clickmart.backend.features.support.service.SupportService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/support")
public class SupportController {

    private final SupportService supportService;

    @Autowired
    public SupportController(SupportService supportService) {
        this.supportService = supportService;
    }

    
    @PostMapping("/tickets")
    public ResponseEntity<ApiResponse<TicketDTO>> submitTicket(@Valid @RequestBody TicketRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "Ticket submitted", supportService.submitTicket(req)));
    }

    
    @GetMapping("/tickets/my")
    public ResponseEntity<ApiResponse<List<TicketDTO>>> getMyTickets() {
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(true, "Tickets fetched", supportService.getMyTickets()));
    }

    
    @GetMapping("/tickets")
    public ResponseEntity<ApiResponse<Page<TicketDTO>>> getAllTickets(
            @RequestParam(name = "query", required = false) String query,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(true, "Tickets fetched",
                supportService.getAllTickets(query, page, size)));
    }

    
    @PatchMapping("/tickets/{id}/respond")
    public ResponseEntity<ApiResponse<TicketDTO>> respond(
            @PathVariable("id") Long id, @Valid @RequestBody TicketResponseRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(true, "Response sent",
                supportService.respondToTicket(id, request)));
    }
}
