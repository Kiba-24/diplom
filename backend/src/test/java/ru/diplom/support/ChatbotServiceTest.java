package ru.diplom.support;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.diplom.support.service.ChatbotService;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
class ChatbotServiceTest {
    @Autowired
    ChatbotService chatbotService;

    @Test
    void shouldClassifyPasswordIntent() {
        var bot = chatbotService.process(1L, "Не могу сменить пароль");
        assertTrue(bot.getText().contains("RESET_PASSWORD"));
    }
}
