package com.meli.exam.mutant.services;

import com.meli.exam.mutant.iservices.IStrandConfigurationDetector;
import com.meli.exam.mutant.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.meli.exam.mutant.utils.Constants.NITROGENOUS_MUTANT_PATTERN;

@Service
public class StrandConfigurationDetector implements IStrandConfigurationDetector {



    @Autowired
    StrandConfigurationBuilder strandConfigurationBuilder;

    /**
     * The initial dna strands configuration, correspond to the vertical strands configurations,
     * then the first step is verified the number of strands with 4 letters equals in this sequence.<br>
     * If the number of strands found is bigger than the minimum number of matches to identified a mutant,
     * then this values will be returned.<br>
     * Else, will be built the horizontal and obliques strands configuration array based on horizontal strands configuration array.<br>
     * In this new array will be verified if there are strands with 4 equal letters and this value will be added to the matches
     * in the configuration of the vertical strands.<br>
     *
     * @param dna
     * @return the number of matches found in dna
     */
    @Override
    public int numberOfStrandsConfigurationFoundToBeMutant(List<String> dna) {
        int matches = mutantStrandsConfigurationFinder(dna);
        if (matches < Constants.MINIMUM_NUMBER_MATCHES_TO_BE_MUTANT) {
            matches += mutantStrandsConfigurationFinder(strandConfigurationBuilder.getVerticalObliqueStrandConfiguration(dna));
        }
        return matches;
    }


    /**
     * For each one of the elements of the array, the matching with the pattern NITROGENOUS_MUTANT_PATTERN will be searched,
     * and this value will be accumulated until the whole array is traversed.
     *
     * @param strandsConfiguration
     * @return the count of matches in strandsConfiguration
     */
    @Override
    public int mutantStrandsConfigurationFinder(List<? extends CharSequence> strandsConfiguration) {
        return strandsConfiguration.stream().mapToInt(s -> (int) NITROGENOUS_MUTANT_PATTERN.matcher(s).results().count()).sum();
    }


}
