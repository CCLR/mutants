package com.meli.exam.mutant.services;

import com.meli.exam.mutant.iservices.IMutantStatsBuilder;
import com.meli.exam.mutant.pojos.MutantStats;
import com.meli.exam.mutant.repository.DnaVerifiedDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class MutantStatsBuilder implements IMutantStatsBuilder {

    @Autowired
    private DnaVerifiedDao dnaVerifiedDao;

    /**
     * Create the entity MutantStats
     * Fill MutantCount with the value returned to method getCountDnaVerified with flagMutant equal to 1
     * Fill HumanCount with the value returned to method getCountDnaVerified with flagMutant equal to 0
     * Calculate the ratio of mutants to humans if humanCount is different to 0 else ratio is equal to MutantCount
     *
     * @return MutantStats entity
     */
    @Override
    public MutantStats getMutantStats() {
        var mutantStats = new MutantStats();
        mutantStats.setMutantCount(dnaVerifiedDao.getCountDnaVerified(1));
        mutantStats.setHumanCount(dnaVerifiedDao.getCountDnaVerified(0));

        var ratio = new BigDecimal(mutantStats.getMutantCount());
        if(mutantStats.getHumanCount() != 0){
            ratio = ratio.divide(new BigDecimal(mutantStats.getHumanCount()), 2, RoundingMode.HALF_UP);
        }
        mutantStats.setRatio(ratio);

        return mutantStats;
    }
}
