package ru.diplom.support.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.diplom.support.model.ChatMessage;
import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findByTicketIdOrderByCreatedAtAsc(Long ticketId);
}
