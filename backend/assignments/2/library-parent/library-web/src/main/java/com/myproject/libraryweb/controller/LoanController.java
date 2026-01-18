package com.myproject.libraryweb.controller;

import com.myproject.libraryapi.dtos.LoanResponse;
import com.myproject.librarydomain.entities.Loan;
import com.myproject.libraryservice.service.LoanService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/loans")
public class LoanController {

    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @Operation(
            summary = "Borrow a book",
            description = "Allows a MEMBER to borrow a book if it is AVAILABLE."
    )
    @PostMapping("/{bookId}/borrow")
    @ResponseStatus(HttpStatus.CREATED)
    public LoanResponse borrow(
            @PathVariable UUID bookId,
            Authentication authentication) {

        Loan loan = loanService.borrowBook(bookId, authentication.getName());

        return LoanResponse.builder()
                .id(loan.getId())
                .bookId(loan.getBook().getId())
                .borrowerId(loan.getBorrower().getId())
                .borrowedAt(loan.getBorrowedAt())
                .returnedAt(loan.getReturnedAt())
                .build();
    }


    @PostMapping("/{bookId}/return")
    public LoanResponse returnBook(@PathVariable UUID bookId,
                                   Authentication authentication) {

        Loan loan = loanService.returnBook(bookId, authentication.getName());

        return LoanResponse.builder()
                .id(loan.getId())
                .bookId(loan.getBook().getId())
                .borrowerId(loan.getBorrower().getId())
                .borrowedAt(loan.getBorrowedAt())
                .returnedAt(loan.getReturnedAt())
                .build();
    }
}
