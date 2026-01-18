package com.myproject.librarydomain.repositories;

import com.myproject.libraryapi.dtos.enums.BookStatus;
import com.myproject.librarydomain.entities.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BookRepository extends JpaRepository<Book, UUID> {
    Page<Book> findByStatus(BookStatus status, Pageable pageable);

    Page<Book> findByTitleContainingIgnoreCase(String title, Pageable pageable);

    Page<Book> findByStatusAndTitleContainingIgnoreCase(
            BookStatus status,
            String title,
            Pageable pageable
    );
}