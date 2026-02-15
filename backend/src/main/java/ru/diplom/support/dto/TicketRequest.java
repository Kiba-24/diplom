package ru.diplom.support.dto;

import jakarta.validation.constraints.NotBlank;
import ru.diplom.support.model.Priority;

public record TicketRequest(@NotBlank String title, @NotBlank String description, String category, Priority priority) {}
