package com.meli.exam.mutant.exception.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@Getter
@AllArgsConstructor
public class ErrorModel {

    private Map<String, String> errors;
}
