package com.meli.exam.mutant.services;

import com.meli.exam.mutant.repository.DnaVerifiedDao;
import com.meli.exam.mutant.exception.DnaStrandsLengthException;
import com.meli.exam.mutant.iservices.IMutantValidator;
import com.meli.exam.mutant.iservices.IStrandConfigurationDetector;
import com.meli.exam.mutant.pojos.DnaSequence;
import com.meli.exam.mutant.pojos.DnaVerified;
import com.meli.exam.mutant.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.IntPredicate;
import java.util.function.Predicate;

@Service
public class MutantValidator implements IMutantValidator {

    @Autowired
    IStrandConfigurationDetector strandConfigurationService;

    @Autowired
    private DnaVerifiedDao dnaVerifiedDao;

    /**
     * Call a method to found the number of possible strands configurations with 4 equals letters based in the list of strings in dnaSequence
     * if this number is greater than or equal to 2, the DNA sequence is a mutant, else is a human
     * Call a method to save this DNA sequence with the flag isMutant to identify the mutants
     *
     * @param dnaSequence
     * @return True if  a mutant is detected  in DNA sequence
     *         False if a mutant is not detected  in DNA sequence
     */
    @Override
    public Boolean isMutant(DnaSequence dnaSequence) {
        int numberStrandsConfigurationWith4EqualLetters = strandConfigurationService.numberOfStrandsConfigurationFoundToBeMutant(dnaSequence.getDna());

        IntPredicate matchesBeingMutant = counterMatches -> counterMatches >= Constants.MINIMUM_NUMBER_MATCHES_TO_BE_MUTANT;
        dnaVerifiedDao.createDnaVerified(new DnaVerified(dnaSequence.getDna().toString(), matchesBeingMutant.test(numberStrandsConfigurationWith4EqualLetters)));

        return matchesBeingMutant.test(numberStrandsConfigurationWith4EqualLetters);
    }

    /**
     * Validate if the any String in the dna array has a different length of size the array
     * the matrix must be NxN to be valid
     * if a Strand is found with a different length, then a DNA strands length exception is throw
     *
     * @param dnaSequence
     */
    @Override
    public void validateIfDnaStrandsIsOk(DnaSequence dnaSequence) {
        int arrayLength = dnaSequence.getDna().size();
        Predicate<String> differentSize = strand -> strand.length() != arrayLength;
        Optional<String> inconsistencyStrand = dnaSequence.getDna().stream().filter(differentSize).findFirst();
        inconsistencyStrand.ifPresent(strand -> {
            throw new DnaStrandsLengthException(arrayLength, strand.length());
        });
    }

}
