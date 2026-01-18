package com.myproject.libraryservice.service;

import com.myproject.libraryapi.dtos.enums.BookStatus;
import com.myproject.librarydomain.entities.Book;
import com.myproject.librarydomain.repositories.BookRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * Provides analytical views over library data.
 */
@Service
public class AnalyticsService {

    private final BookRepository bookRepository;

    public AnalyticsService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    /**
     * Returns count of books grouped by their status.
     *
     * @return map of BookStatus -> count
     */
    @PreAuthorize("hasAnyRole('MEMBER','LIBRARIAN')")
    public Map<BookStatus, Long> getBookStatusCounts() {

        return bookRepository.findAll()
                .stream()
                .collect(Collectors.groupingBy(
                        Book::getStatus,
                        Collectors.counting()
                ));
    }
}