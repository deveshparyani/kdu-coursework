package com.myproject.libraryapi.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * Request DTO for creating a new book.
 */
@Data
public class CreateBookRequest {

    @NotBlank(message = "title must not be blank")
    @Size(min = 2, message = "title must be at least 2 characters long")
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}