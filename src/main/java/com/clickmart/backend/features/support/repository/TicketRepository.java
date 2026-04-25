package com.clickmart.backend.features.support.repository;

import com.clickmart.backend.entity.Ticket;
import com.clickmart.backend.enums.TicketStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findByUserIdOrderByCreatedAtDesc(Long userId);
    Optional<Ticket> findByTicketNumber(String ticketNumber);
    Optional<Ticket> findByIdAndUserId(Long id, Long userId);
    Page<Ticket> findAll(Pageable pageable);
    long countByStatus(TicketStatus status);

    @Query("SELECT t FROM Ticket t WHERE " +
           "LOWER(t.subject) LIKE LOWER(CONCAT('%',:q,'%')) OR " +
           "LOWER(t.ticketNumber) LIKE LOWER(CONCAT('%',:q,'%')) OR " +
           "LOWER(t.user.email) LIKE LOWER(CONCAT('%',:q,'%'))")
    Page<Ticket> searchTickets(@Param("q") String query, Pageable pageable);
}
