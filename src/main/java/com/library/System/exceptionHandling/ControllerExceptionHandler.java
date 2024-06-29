package com.library.System.exceptionHandling;

import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.nio.file.FileAlreadyExistsException;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ControllerExceptionHandler {


    @ExceptionHandler(ChangeSetPersister.NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorMessage handleNotFoundException(ChangeSetPersister.NotFoundException e) {
        return ErrorMessage.builder().message(e.getMessage()).build();
    }

    @ExceptionHandler({ FileAlreadyExistsException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public ErrorMessage isExistedException(RuntimeException e) {
        return  ErrorMessage.builder().message(e.getMessage()).build();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorMessage handleException(Exception e) {
        return  ErrorMessage.builder().message(e.getMessage()).build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorMessage notValidArgumentException(MethodArgumentNotValidException e) {
        List<ErrorMessageDetail> detailsDtos = e.getFieldErrors().stream()
                .map(fieldError -> new ErrorMessageDetail(fieldError.getField(), fieldError.getDefaultMessage())).toList();
        return  ErrorMessage.builder().message(String.valueOf(detailsDtos)).build();
    }



}
