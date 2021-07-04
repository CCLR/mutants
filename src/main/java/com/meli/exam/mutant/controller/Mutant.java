package com.meli.exam.mutant.controller;

import com.meli.exam.mutant.iservices.IMutantValidator;
import com.meli.exam.mutant.pojos.DnaSequence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/mutant")
public class Mutant {

    @Autowired
    IMutantValidator mutantService;

    /**
     * Detects whether a human is a mutant based on his or her DNA sequence
     *
     * @param dnaSequence
     * @return In case of verifying a mutant, it should return a HTTP 200-OK
     *         In case of verifying a human, it should return a HTTP 403-FORBIDDEN
     *         In case of DNA sequence is not valid, it should return a HTTP 400-BAD REQUEST
     */
    @PostMapping("/")
    public ResponseEntity<Void> isMutant(@Valid @RequestBody DnaSequence dnaSequence) {
        mutantService.validateIfDnaStrandsIsOk(dnaSequence);
        boolean mutantFlag = mutantService.isMutant(dnaSequence);
        if (mutantFlag) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

    }

}
