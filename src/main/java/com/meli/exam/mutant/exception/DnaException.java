package com.meli.exam.mutant.exception;

import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class DnaException extends RuntimeException {


    private final String  code;
    private final String errorMessage;
    private final HttpStatus statusCode;

}
