package com.meli.exam.mutant.exception;

import org.springframework.http.HttpStatus;
import java.text.MessageFormat;
import com.meli.exam.mutant.utils.Constants;

public class DnaStrandsLengthException extends DnaException {

    /**
     * Build the Exception with expected length and found length
     * @param expected
     * @param found
     */
    public DnaStrandsLengthException(int expected, int found) {
        super(Constants.CODE_ERROR_DNA_STRANDS_LENGTH_NOT_VALID, MessageFormat.format(Constants.MESSAGE_ERROR_DNA_STRANDS_LENGTH_NOT_VALID, expected, found), HttpStatus.BAD_REQUEST );
    }

}
