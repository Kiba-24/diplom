package ru.diplom.support.controller;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import ru.diplom.support.dto.ChatRequest;
import ru.diplom.support.model.ChatMessage;
import ru.diplom.support.service.ChatbotService;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
public class ChatController {
    private final ChatbotService chatbotService;
    public ChatController(ChatbotService chatbotService) { this.chatbotService = chatbotService; }

    @PostMapping
    public ChatMessage chat(@Valid @RequestBody ChatRequest request) {
        return chatbotService.process(request.ticketId(), request.message());
    }

    @GetMapping("/{ticketId}")
    public List<ChatMessage> history(@PathVariable Long ticketId) {
        return chatbotService.history(ticketId);
    }
}
