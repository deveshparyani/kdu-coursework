package com.myproject.libraryweb.controller;

import com.myproject.libraryapi.dtos.BookResponse;
import com.myproject.libraryapi.dtos.CreateBookRequest;
import com.myproject.libraryapi.dtos.enums.BookStatus;
import com.myproject.librarydomain.entities.Book;

import com.myproject.libraryservice.service.BookService;
import com.myproject.libraryweb.utils.ConstantsUtils;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(ConstantsUtils.API_BASE_URL + "/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @Operation(
            summary = "Create a new book",
            description = "Creates a book in PROCESSING state. Only LIBRARIAN can perform this action."
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookResponse createBook(
            @Valid @RequestBody CreateBookRequest request) {

        Book book = bookService.createBook(request.getTitle());
        return BookResponse.builder()
                .id(book.getId())
                .title(book.getTitle())
                .status(book.getStatus())
                .createdAt(book.getCreatedAt())
                .build();
    }

    @Operation(
            summary = "Catalog a book",
            description = "Transitions book status from PROCESSING to AVAILABLE. Only LIBRARIAN can perform this action."
    )
    @PatchMapping("/{id}/catalog")
    public Book catalogBook(@PathVariable UUID id) {
        return bookService.catalogBook(id);
    }

    @GetMapping
    public Page<BookResponse> getBooks(@RequestParam(required = false) BookStatus status,
                                       @RequestParam(required = false) String titleContains,
                                       Pageable pageable) {

        return bookService.getAllBooks(status, titleContains, pageable)
                .map(book -> BookResponse.builder()
                        .id(book.getId())
                        .title(book.getTitle())
                        .status(book.getStatus())
                        .createdAt(book.getCreatedAt())
                        .build()
                );
    }
}
