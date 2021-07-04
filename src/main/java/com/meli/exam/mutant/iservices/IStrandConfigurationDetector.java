package com.meli.exam.mutant.iservices;

import java.util.List;

public interface IStrandConfigurationDetector {


    int numberOfStrandsConfigurationFoundToBeMutant(List<String> dna);

    int mutantStrandsConfigurationFinder(List<? extends CharSequence> strandsConfiguration);


}
