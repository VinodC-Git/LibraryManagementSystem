package com.library.System.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookResponseDTO  {
      Long id;
      @NotNull(message = "isbn should not be empty") String isbn;
      @NotNull(message = "title should not be empty") String title;
      @NotNull(message = "author should not be empty") String author;


}