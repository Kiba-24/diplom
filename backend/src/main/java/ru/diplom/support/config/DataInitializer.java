package ru.diplom.support.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.diplom.support.model.*;
import ru.diplom.support.repository.*;

import java.util.List;

@Configuration
public class DataInitializer {
    @Bean
    CommandLineRunner seed(UserRepository users, CategoryRepository categories, TicketRepository tickets, PasswordEncoder encoder) {
        return args -> {
            if (users.count() > 0) return;

            AppUser admin = new AppUser();
            admin.setUsername("admin");
            admin.setPassword(encoder.encode("admin123"));
            admin.setRole(Role.ADMIN);
            users.save(admin);

            AppUser operator = new AppUser();
            operator.setUsername("operator");
            operator.setPassword(encoder.encode("operator123"));
            operator.setRole(Role.OPERATOR);
            users.save(operator);

            AppUser user = new AppUser();
            user.setUsername("user");
            user.setPassword(encoder.encode("user123"));
            user.setRole(Role.USER);
            users.save(user);

            List<Category> cats = categories.saveAll(List.of(new Category("Сеть"), new Category("ПО"), new Category("Оборудование"), new Category("Доступ")));
            for (int i = 1; i <= 50; i++) {
                Ticket t = new Ticket();
                t.setTitle("Тестовая заявка #" + i);
                t.setDescription("Описание проблемы по заявке #" + i + " (vpn/password/outlook)");
                t.setCategory(cats.get(i % cats.size()));
                t.setPriority(Priority.values()[i % Priority.values().length]);
                t.setStatus(TicketStatus.values()[i % TicketStatus.values().length]);
                t.setRequester(user);
                t.setOperator(operator);
                tickets.save(t);
            }
        };
    }
}
