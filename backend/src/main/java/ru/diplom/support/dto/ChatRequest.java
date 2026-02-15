package ru.diplom.support.dto;

import jakarta.validation.constraints.NotBlank;

public record ChatRequest(Long ticketId, @NotBlank String message) {}
