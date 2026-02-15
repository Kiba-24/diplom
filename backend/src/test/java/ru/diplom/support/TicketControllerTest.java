package ru.diplom.support;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import ru.diplom.support.model.AppUser;
import ru.diplom.support.model.Role;
import ru.diplom.support.repository.UserRepository;
import ru.diplom.support.security.TokenService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class TicketControllerTest {
    @Autowired MockMvc mvc;
    @Autowired UserRepository users;
    @Autowired PasswordEncoder encoder;
    @Autowired TokenService tokenService;

    @Test
    void shouldCreateTicket() throws Exception {
        AppUser user = users.findByUsername("testuser").orElseGet(() -> {
            AppUser u = new AppUser();
            u.setUsername("testuser");
            u.setPassword(encoder.encode("123"));
            u.setRole(Role.USER);
            return users.save(u);
        });
        String token = tokenService.issueToken(user);
        mvc.perform(post("/api/tickets")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\":\"VPN\",\"description\":\"Не работает VPN\",\"category\":\"Сеть\"}"))
                .andExpect(status().isOk());
    }
}
