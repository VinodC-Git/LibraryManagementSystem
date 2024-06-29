package com.library.System.exceptionHandling;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ErrorMessageDetail {

    private String fieldName;
    private String errorDescription;
}
