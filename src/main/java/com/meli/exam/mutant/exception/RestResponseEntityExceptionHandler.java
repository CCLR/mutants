package com.meli.exam.mutant.exception;

import com.meli.exam.mutant.exception.model.ErrorModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.*;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * This exception is thrown when an invalid argument is found in the Mutant controller payload
     *
     * @param ex
     * @param headers
     * @param status
     * @param request
     * @return error model with a map of errors
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        var error = new ErrorModel(errors);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    /**
     * This exception is thrown when the size of any of the strings in the DNA sequence is different from the size of the array DNA
     *
     * @param ex
     * @param request
     * @return error model with a map of errors
     */
    @ExceptionHandler({DnaException.class})
    protected ResponseEntity<Object> dnaHandlerException(DnaException ex, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        errors.put(ex.getCode(), ex.getErrorMessage());
        var error = new ErrorModel(errors);
        return new ResponseEntity<>(error, ex.getStatusCode());
    }
}
