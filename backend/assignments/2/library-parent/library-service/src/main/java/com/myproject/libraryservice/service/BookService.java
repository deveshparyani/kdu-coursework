package com.myproject.libraryservice.service;

import com.myproject.libraryapi.dtos.enums.BookStatus;
import com.myproject.librarydomain.entities.Book;
import com.myproject.librarydomain.repositories.BookRepository;
import com.myproject.libraryservice.exception.InvalidStateException;
import com.myproject.libraryservice.exception.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @AuditLog(action = "CREATE_BOOK")
    @PreAuthorize("hasRole('LIBRARIAN')")
    public Book createBook(String title) {
        Book book = new Book(title, BookStatus.PROCESSING);
        return bookRepository.save(book);
    }

    @AuditLog(action = "CATALOG_BOOK")
    @PreAuthorize("hasRole('LIBRARIAN')")
    public Book catalogBook(UUID bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() ->
                new NotFoundException("Book not found: " + bookId)
        );

        if (book.getStatus() != BookStatus.PROCESSING) {
            throw new InvalidStateException(
                    "Only PROCESSING books can be cataloged"
            );
        }

        book.setStatus(BookStatus.AVAILABLE);
        return book;
    }

    @PreAuthorize("hasAnyRole('LIBRARIAN', 'MEMBER')")
    public Page<Book> getAllBooks(BookStatus status,
                                  String titleContains,
                                  Pageable pageable) {
        if (status != null && titleContains != null) {
            return bookRepository.findByStatusAndTitleContainingIgnoreCase(
                    status, titleContains, pageable);
        }

        if (status != null) {
            return bookRepository.findByStatus(status, pageable);
        }

        if (titleContains != null) {
            return bookRepository.findByTitleContainingIgnoreCase(
                    titleContains, pageable);
        }

        return bookRepository.findAll(pageable);
    }
}