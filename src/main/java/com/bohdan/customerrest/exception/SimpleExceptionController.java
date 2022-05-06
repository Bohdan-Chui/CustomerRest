package com.bohdan.customerrest.exception;

import lombok.extern.log4j.Log4j2;
import org.hibernate.boot.MappingException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.naming.ConfigurationException;
import javax.persistence.EntityExistsException;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Log4j2
@RestControllerAdvice
public class SimpleExceptionController {

    @ExceptionHandler({EntityExistsException.class, NoSuchElementException.class, IllegalArgumentException.class,
            EntityExistsException.class, EmptyResultDataAccessException.class, NullPointerException.class})
    public ResponseEntity<ApiError> handle(RuntimeException ex) {
        log.error(ex);
        ApiError error = new ApiError(HttpStatus.BAD_REQUEST);
        error.setMessage(ex.getMessage());

        return ResponseEntity
                .status(error.getStatus())
                .body(error);

    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({MappingException.class, ConfigurationException.class})
    public ResponseEntity<ApiError> handleRuntime(RuntimeException ex) {
        log.error(ex);
        ApiError error = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR);
        error.setMessage(ex.getMessage());

        return ResponseEntity
                .status(error.getStatus())
                .body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        log.error(ex);
        ApiError error = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR);
        error.setSubErrors(ex.getBindingResult().getAllErrors().stream().map(exception -> ApiValidationError
                        .builder()
                        .field(((FieldError) exception).getField())
                        .message(exception.getDefaultMessage())
                        .rejectedValue(((FieldError) exception).getRejectedValue())
                        .object(ex.getObjectName())
                        .build())
                .collect(Collectors.toList()));

        return ResponseEntity
                .status(error.getStatus())
                .body(error);
    }
}
