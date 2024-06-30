package com.library.System.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookResponseDTO {
    Long id;

    @NotNull(message = "isbn should not be empty")
    @NotEmpty(message = "isbn cannot be empty")
    String isbn;

    @NotNull(message = "Title cannot be null")
    @NotEmpty(message = "Title cannot be empty")
    String title;

    @NotNull(message = "Author cannot be null")
    @NotEmpty(message = "Author cannot be empty")
    String author;


}