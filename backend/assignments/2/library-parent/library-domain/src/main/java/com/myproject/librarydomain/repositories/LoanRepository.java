package com.myproject.librarydomain.repositories;

import com.myproject.librarydomain.entities.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface LoanRepository extends JpaRepository<Loan, UUID> {
    Optional<Loan> findActiveLoanByBookId(UUID bookId);
}