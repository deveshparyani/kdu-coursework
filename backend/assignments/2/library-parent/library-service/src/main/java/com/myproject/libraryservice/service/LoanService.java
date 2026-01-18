package com.myproject.libraryservice.service;

import com.myproject.libraryapi.dtos.enums.BookStatus;
import com.myproject.librarydomain.entities.Book;
import com.myproject.librarydomain.entities.Loan;
import com.myproject.librarydomain.entities.User;
import com.myproject.librarydomain.repositories.BookRepository;
import com.myproject.librarydomain.repositories.LoanRepository;
import com.myproject.librarydomain.repositories.UserRepository;
import com.myproject.libraryservice.exception.InvalidStateException;
import com.myproject.libraryservice.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
@Transactional
public class LoanService {

    private final BookRepository bookRepository;
    private final LoanRepository loanRepository;
    private final UserRepository userRepository;

    public LoanService(BookRepository bookRepository,
                       LoanRepository loanRepository,
                       UserRepository userRepository) {
        this.bookRepository = bookRepository;
        this.loanRepository = loanRepository;
        this.userRepository = userRepository;
    }

    @AuditLog(action = "BORROW_BOOK")
    public Loan borrowBook(UUID bookId, String username) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new NotFoundException("Book not found"));

        if (book.getStatus() != BookStatus.AVAILABLE) {
            throw new InvalidStateException("BOOK_NOT_AVAILABLE");
        }

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("User not found"));

        Loan loan = new Loan(user, book, Instant.now());
        loan.setReturnedAt(null);

        book.setStatus(BookStatus.CHECKED_OUT);

        loanRepository.save(loan);
        bookRepository.save(book);

        return loan;
    }

    @AuditLog(action = "RETURN_BOOK")
    public Loan returnBook(UUID bookId, String username) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new NotFoundException("Book not found"));

        if (book.getStatus() != BookStatus.CHECKED_OUT) {
            throw new InvalidStateException("BOOK_NOT_CHECKED_OUT");
        }

        Loan loan = loanRepository.findActiveLoanByBookId(bookId)
                .orElseThrow(() -> new NotFoundException("Active loan not found"));

        if (!loan.getBorrower().getUsername().equals(username)) {
            throw new InvalidStateException("ONLY_BORROWER_CAN_RETURN");
        }

        loan.setReturnedAt(Instant.now());
        book.setStatus(BookStatus.AVAILABLE);

        loanRepository.save(loan);
        bookRepository.save(book);

        return loan;
    }
}
