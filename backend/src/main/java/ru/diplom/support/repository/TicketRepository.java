package ru.diplom.support.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.diplom.support.model.*;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findByStatus(TicketStatus status, Sort sort);
    List<Ticket> findByCategory_NameContainingIgnoreCase(String category, Sort sort);
    List<Ticket> findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String title, String description, Sort sort);
}
