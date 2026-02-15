package ru.diplom.support.controller;

import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.diplom.support.dto.TicketRequest;
import ru.diplom.support.model.Ticket;
import ru.diplom.support.model.TicketStatus;
import ru.diplom.support.service.TicketService;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {
    private final TicketService ticketService;
    public TicketController(TicketService ticketService) { this.ticketService = ticketService; }

    @GetMapping
    public List<Ticket> list(@RequestParam(required = false) String search,
                             @RequestParam(required = false) TicketStatus status,
                             @RequestParam(required = false) String category,
                             @RequestParam(required = false) String sort) {
        return ticketService.list(search, status, category, sort);
    }

    @PostMapping
    public Ticket create(@Valid @RequestBody TicketRequest request, Authentication auth) {
        return ticketService.create(request, auth.getName());
    }

    @PatchMapping("/{id}/status")
    public Ticket updateStatus(@PathVariable Long id, @RequestParam TicketStatus status) {
        return ticketService.updateStatus(id, status);
    }

    @DeleteMapping("/{id}")
    public void archive(@PathVariable Long id) { ticketService.archive(id); }

    @GetMapping(value = "/export", produces = "text/csv")
    public String exportCsv() throws IOException { return ticketService.exportCsv(); }

    @PostMapping(value = "/import", consumes = MediaType.TEXT_PLAIN_VALUE)
    public List<Ticket> importCsv(@RequestBody String csv, Authentication auth) throws IOException { return ticketService.importCsv(csv, auth.getName()); }

    @GetMapping("/report")
    public Map<String, Long> report(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
                                    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to) {
        return ticketService.report(from, to);
    }
}
