package ru.diplom.support.service;

import org.springframework.stereotype.Service;
import ru.diplom.support.model.ChatMessage;
import ru.diplom.support.repository.ChatMessageRepository;

import java.util.List;

@Service
public class ChatbotService {
    private final ChatMessageRepository chatMessageRepository;

    public ChatbotService(ChatMessageRepository chatMessageRepository) { this.chatMessageRepository = chatMessageRepository; }

    public ChatMessage process(Long ticketId, String message) {
        ChatMessage userMsg = new ChatMessage();
        userMsg.setTicketId(ticketId);
        userMsg.setSender("USER");
        userMsg.setText(message);
        chatMessageRepository.save(userMsg);

        String normalized = message.toLowerCase().replaceAll("[^\\p{L}\\p{N} ]", " ");
        String intent = normalized.contains("парол") ? "RESET_PASSWORD" : normalized.contains("vpn") ? "VPN_ISSUE" : "GENERAL";
        String responseText = switch (intent) {
            case "RESET_PASSWORD" -> "Похоже, это сброс пароля. Проверьте AD self-service и код из MFA.";
            case "VPN_ISSUE" -> "Проблема с VPN. Уточните код ошибки и проверьте подключение к корпоративной сети.";
            default -> "Запрос принят. Я классифицировал его и передам оператору при необходимости.";
        };

        ChatMessage bot = new ChatMessage();
        bot.setTicketId(ticketId);
        bot.setSender("BOT");
        bot.setText(responseText + " [intent=" + intent + "]");
        return chatMessageRepository.save(bot);
    }

    public List<ChatMessage> history(Long ticketId) {
        return chatMessageRepository.findByTicketIdOrderByCreatedAtAsc(ticketId);
    }
}
