package com.myproject.libraryweb.exception;

import com.myproject.libraryapi.dtos.ErrorResponse;
import com.myproject.libraryapi.dtos.FieldError;
import com.myproject.libraryservice.exception.ConcurrencyException;
import com.myproject.libraryservice.exception.InvalidStateException;
import com.myproject.libraryservice.exception.NotFoundException;
import jakarta.persistence.OptimisticLockException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidation(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        List<FieldError> details =
                ex.getBindingResult()
                        .getFieldErrors()
                        .stream()
                        .map(err -> new FieldError(
                                err.getField(),
                                err.getDefaultMessage()))
                        .toList();

        return buildError(
                request,
                "VALIDATION_ERROR",
                "Invalid request",
                details
        );
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFound(
            NotFoundException ex,
            HttpServletRequest request) {

        return buildError(request, "NOT_FOUND", ex.getMessage(), null);
    }

    @ExceptionHandler({InvalidStateException.class, ConcurrencyException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleConflict(
            RuntimeException ex,
            HttpServletRequest request) {

        return buildError(request, "CONFLICT", ex.getMessage(), null);
    }

//    @ExceptionHandler(Exception.class)
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    public ErrorResponse handleGeneric(
//            Exception ex,
//            HttpServletRequest request) {
//
//        return buildError(
//                request,
//                "INTERNAL_ERROR",
//                "Unexpected error occurred",
//                null
//        );
//    }

    @ExceptionHandler(OptimisticLockException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleOptimisticLock(
            OptimisticLockException ex,
            HttpServletRequest request) {

        return buildError(
                request,
                "CONCURRENT_MODIFICATION",
                "Concurrent update detected",
                null
        );
    }

    private ErrorResponse buildError(
            HttpServletRequest request,
            String code,
            String message,
            List<FieldError> details) {

        ErrorResponse error = new ErrorResponse();
        error.setTimestamp(Instant.now());
        error.setPath(request.getRequestURI());
        error.setErrorCode(code);
        error.setMessage(message);
        error.setDetails(details);
        error.setCorrelationId(
                MDC.get("correlationId")
        );
        return error;
    }
}