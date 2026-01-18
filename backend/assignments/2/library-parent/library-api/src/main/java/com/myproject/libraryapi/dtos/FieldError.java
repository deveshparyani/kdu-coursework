package com.myproject.libraryapi.dtos;

import lombok.Data;

/**
 * Represents a validation error for a specific field.
 */
@Data
public class FieldError {

    private String field;
    private String issue;

    public FieldError(String field, String issue) {
        this.field = field;
        this.issue = issue;
    }

    // getters
}