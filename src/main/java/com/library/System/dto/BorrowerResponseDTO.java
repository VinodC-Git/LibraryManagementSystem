package com.library.System.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public final class BorrowerResponseDTO {
    private Long id;
    private @NotEmpty(message = "name should not be empty")
    String name;
    @Email(message = "email should not be empty")
    private String emailAddress;


}