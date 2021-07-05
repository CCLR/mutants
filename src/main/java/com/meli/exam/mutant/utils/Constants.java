package com.meli.exam.mutant.utils;

import java.util.regex.Pattern;

public  class Constants {

    private Constants() { }

    public static final Pattern NITROGENOUS_MUTANT_PATTERN = Pattern.compile("([ACTG])\\1{3}", Pattern.CASE_INSENSITIVE);
    public static final int MINIMUM_NUMBER_MATCHES_TO_BE_MUTANT = 2;
    public static final int NUMBER_OF_IDENTICAL_LETTERS = 4;

    public static final String MESSAGE_ERROR_DNA_NOT_EMPTY = "Dna cannot be empty.";
    public static final String MESSAGE_ERROR_CHARACTERS_NOT_ALLOW =  "only the letters (A,T,C,G) are allowed";
    public static final String MESSAGE_ERROR_DNA_STRANDS_LENGTH_NOT_VALID =  "The length of the DNA strands must be the same size as the DNA array. Expected {0}, found {1}";
    public static final String CODE_ERROR_DNA_STRANDS_LENGTH_NOT_VALID =  "dnaStrandsLengthNotValid";

    public static final String DNA_EMPTY =  "dnaEmpty";
    public static final String DNA_STRANDS_CHARACTERS_NOT_ALLOW =  "dnaStrandsCharactersNotAllow";
    public static final String DNA_SEQUENCE_NOT_VALID =  "dnaSequenceNotValid";
    public static final String DNA_STRANDS_CONFIGURATION_MUTANT =  "dnaStrandsConfigurationMutant";
    public static final String DNA_STRANDS_CONFIGURATION_MUTANT_HORIZONTAL =  "dnaStrandsConfigurationMutantHorizontal";
    public static final String DNA_STRANDS_CONFIGURATION_HUMAN =  "dnaStrandsConfigurationHuman";

}
