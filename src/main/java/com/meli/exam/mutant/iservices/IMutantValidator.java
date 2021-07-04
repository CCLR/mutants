package com.meli.exam.mutant.iservices;

import com.meli.exam.mutant.pojos.DnaSequence;

public interface IMutantValidator {

    Boolean isMutant(DnaSequence dnaSequence);


    void validateIfDnaStrandsIsOk(DnaSequence dnaSequence);

}
