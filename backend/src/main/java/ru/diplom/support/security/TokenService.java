package ru.diplom.support.security;

import org.springframework.stereotype.Service;
import ru.diplom.support.model.AppUser;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class TokenService {
    private final Map<String, SessionInfo> tokens = new ConcurrentHashMap<>();

    public String issueToken(AppUser user) {
        String token = UUID.randomUUID().toString();
        tokens.put(token, new SessionInfo(user.getUsername(), user.getRole().name(), Instant.now().plusSeconds(3600)));
        return token;
    }

    public SessionInfo validate(String token) {
        SessionInfo info = tokens.get(token);
        if (info == null || info.expiresAt().isBefore(Instant.now())) {
            tokens.remove(token);
            return null;
        }
        return info;
    }

    public record SessionInfo(String username, String role, Instant expiresAt) {}
}
