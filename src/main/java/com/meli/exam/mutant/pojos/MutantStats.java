package com.meli.exam.mutant.pojos;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class MutantStats {
    @JsonProperty("count_mutant_dna")
    private int mutantCount;
    @JsonProperty("count_human_dna")
    private int humanCount;
    private BigDecimal ratio;
}
