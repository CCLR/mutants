package com.meli.exam.mutant.pojos;

import com.meli.exam.mutant.utils.Constants;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;



@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DnaSequence {

    @NotNull
    @NotEmpty(message = Constants.MESSAGE_ERROR_DNA_NOT_EMPTY)
    private List<@Pattern(regexp = "[ATCG]+", flags = Pattern.Flag.CASE_INSENSITIVE, message = Constants.MESSAGE_ERROR_CHARACTERS_NOT_ALLOW) String> dna;

}
