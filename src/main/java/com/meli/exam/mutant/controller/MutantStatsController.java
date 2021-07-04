package com.meli.exam.mutant.controller;

import com.meli.exam.mutant.iservices.IMutantStatsBuilder;
import com.meli.exam.mutant.pojos.MutantStats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/stats")
public class MutantStatsController {

    @Autowired
    IMutantStatsBuilder mutantStatsBuilder;

    /**
     *
     * @return MutantStats with the detected DNA statistics
     */
    @GetMapping("/")
    public ResponseEntity<MutantStats> retriveMutantStats() {
        mutantStatsBuilder.getMutantStats();
        return ResponseEntity.ok(mutantStatsBuilder.getMutantStats());
    }

}
