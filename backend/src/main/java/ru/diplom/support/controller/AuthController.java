package ru.diplom.support.controller;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import ru.diplom.support.dto.AuthRequest;
import ru.diplom.support.dto.AuthResponse;
import ru.diplom.support.model.Role;
import ru.diplom.support.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;
    public AuthController(AuthService authService) { this.authService = authService; }

    @PostMapping("/register")
    public AuthResponse register(@Valid @RequestBody AuthRequest request) {
        String token = authService.register(request, Role.OPERATOR);
        return new AuthResponse(token, Role.OPERATOR.name());
    }

    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody AuthRequest request) {
        String token = authService.login(request);
        return new AuthResponse(token, "UNKNOWN");
    }
}
