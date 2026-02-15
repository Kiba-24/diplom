package ru.diplom.support.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.diplom.support.dto.AuthRequest;
import ru.diplom.support.model.AppUser;
import ru.diplom.support.model.Role;
import ru.diplom.support.repository.UserRepository;
import ru.diplom.support.security.TokenService;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, TokenService tokenService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
    }

    public String register(AuthRequest request, Role role) {
        AppUser user = new AppUser();
        user.setUsername(request.username());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setRole(role);
        return tokenService.issueToken(userRepository.save(user));
    }

    public String login(AuthRequest request) {
        AppUser user = userRepository.findByUsername(request.username()).orElseThrow();
        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid credentials");
        }
        return tokenService.issueToken(user);
    }
}
