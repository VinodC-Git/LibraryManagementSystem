package com.library.System.exceptionHandling;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class ErrorMessage {


    private String message;
    List<ErrorMessageDetail> errorDesc;

}
