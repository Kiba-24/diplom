package ru.diplom.support.service;

import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.diplom.support.dto.TicketRequest;
import ru.diplom.support.model.*;
import ru.diplom.support.repository.CategoryRepository;
import ru.diplom.support.repository.TicketRepository;
import ru.diplom.support.repository.UserRepository;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class TicketService {
    private final TicketRepository ticketRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    public TicketService(TicketRepository ticketRepository, CategoryRepository categoryRepository, UserRepository userRepository) {
        this.ticketRepository = ticketRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }

    public Ticket create(TicketRequest request, String username) {
        AppUser requester = userRepository.findByUsername(username).orElseThrow();
        Category category = categoryRepository.findByName(Optional.ofNullable(request.category()).orElse("Общее"))
                .orElseGet(() -> categoryRepository.save(new Category(Optional.ofNullable(request.category()).orElse("Общее"))));
        Ticket ticket = new Ticket();
        ticket.setTitle(request.title());
        ticket.setDescription(request.description());
        ticket.setCategory(category);
        ticket.setRequester(requester);
        ticket.setPriority(Optional.ofNullable(request.priority()).orElse(Priority.MEDIUM));
        return ticketRepository.save(ticket);
    }

    public List<Ticket> list(String search, TicketStatus status, String category, String sort) {
        Sort s = Sort.by(Optional.ofNullable(sort).orElse("createdAt")).descending();
        if (status != null) return ticketRepository.findByStatus(status, s);
        if (category != null && !category.isBlank()) return ticketRepository.findByCategory_NameContainingIgnoreCase(category, s);
        if (search != null && !search.isBlank()) return ticketRepository.findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(search, search, s);
        return ticketRepository.findAll(s);
    }

    public Ticket updateStatus(Long id, TicketStatus status) {
        Ticket ticket = ticketRepository.findById(id).orElseThrow();
        ticket.setStatus(status);
        return ticketRepository.save(ticket);
    }

    public void archive(Long id) { updateStatus(id, TicketStatus.ARCHIVED); }

    public String exportCsv() throws IOException {
        CsvMapper mapper = new CsvMapper();
        CsvSchema schema = mapper.schemaFor(TicketCsvRow.class).withHeader();
        List<TicketCsvRow> rows = ticketRepository.findAll().stream().map(TicketCsvRow::from).toList();
        return mapper.writer(schema).writeValueAsString(rows);
    }

    public List<Ticket> importCsv(String csv, String username) throws IOException {
        CsvMapper mapper = new CsvMapper();
        CsvSchema schema = mapper.schemaFor(TicketCsvRow.class).withHeader();
        List<TicketCsvRow> rows = Arrays.asList(mapper.readerFor(TicketCsvRow.class).with(schema).<TicketCsvRow>readValues(csv).readAll().toArray(new TicketCsvRow[0]));
        List<Ticket> created = new ArrayList<>();
        for (TicketCsvRow row : rows) {
            created.add(create(new TicketRequest(row.title, row.description, row.category, Priority.valueOf(row.priority)), username));
        }
        return created;
    }

    public Map<String, Long> report(LocalDateTime from, LocalDateTime to) {
        return ticketRepository.findAll().stream()
                .filter(t -> !t.getCreatedAt().isBefore(from) && !t.getCreatedAt().isAfter(to))
                .collect(java.util.stream.Collectors.groupingBy(t -> t.getStatus().name(), java.util.stream.Collectors.counting()));
    }

    public static class TicketCsvRow {
        public String title;
        public String description;
        public String category;
        public String priority;

        public static TicketCsvRow from(Ticket t) {
            TicketCsvRow row = new TicketCsvRow();
            row.title = t.getTitle();
            row.description = t.getDescription();
            row.category = t.getCategory() != null ? t.getCategory().getName() : "Общее";
            row.priority = t.getPriority().name();
            return row;
        }
    }
}
