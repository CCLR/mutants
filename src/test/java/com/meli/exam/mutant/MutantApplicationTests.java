package com.meli.exam.mutant;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.meli.exam.mutant.iservices.IMutantValidator;
import com.meli.exam.mutant.iservices.IStrandConfigurationBuilder;
import com.meli.exam.mutant.iservices.IStrandConfigurationDetector;
import com.meli.exam.mutant.pojos.DnaSequence;
import com.meli.exam.mutant.utils.Constants;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.meli.exam.mutant.utils.Constants.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("dev")
class MutantApplicationTests {

    @Autowired
    private WebApplicationContext appContext;

    private MockMvc mockMvc;

    private Map<String, List<String>> dnaCases;

    @BeforeAll
    void setup() {
        dnaCases = new HashMap<>();
        dnaCases.put(DNA_EMPTY,  List.of());
        dnaCases.put(DNA_STRANDS_CHARACTERS_NOT_ALLOW,  List.of("1TGC", "DAGT", "QTAT", "AZAP"));
        dnaCases.put(DNA_SEQUENCE_NOT_VALID,  List.of("ATGC","CAGT","TTAT"));
        dnaCases.put(DNA_STRANDS_CONFIGURATION_MUTANT,  List.of("ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"));
        dnaCases.put(DNA_STRANDS_CONFIGURATION_MUTANT_HORIZONTAL,  List.of("AAAAAA","TTTTTT","TTATGT","AGAAGG","CCCCTA","TCACTG"));
        dnaCases.put(DNA_STRANDS_CONFIGURATION_HUMAN,  List.of("ATGCGA","CAGTGC","TTATTT","AGACGG","GCGTCA","TCACTG"));
        mockMvc = MockMvcBuilders.webAppContextSetup(appContext).build();
    }

    @Test
    void ifDnaIsEmpty() throws Exception    {
        mockMvc.perform(post("/mutant/")
                .content(asJsonString(new DnaSequence(dnaCases.get(DNA_EMPTY))))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors.dna").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors.dna").value(MESSAGE_ERROR_DNA_NOT_EMPTY));
    }

    @Test
    void ifDnaStrandsHaveCharactersNotAllow() throws Exception    {
        mockMvc.perform(post("/mutant/")
                .content(asJsonString(new DnaSequence(dnaCases.get(DNA_STRANDS_CHARACTERS_NOT_ALLOW))))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors.['dna[0]']").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors.['dna[1]']").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors.['dna[2]']").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors.['dna[3]']").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors.['dna[0]']").value(MESSAGE_ERROR_CHARACTERS_NOT_ALLOW));    }

    @Test
    void ifDnaSequenceIsNotValid() throws Exception    {
        mockMvc.perform(post("/mutant/")
                .content(asJsonString(new DnaSequence(dnaCases.get(DNA_SEQUENCE_NOT_VALID))))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors."+ CODE_ERROR_DNA_STRANDS_LENGTH_NOT_VALID).exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors."+ CODE_ERROR_DNA_STRANDS_LENGTH_NOT_VALID).value(MessageFormat.format(MESSAGE_ERROR_DNA_STRANDS_LENGTH_NOT_VALID, "3", "4")));

    }

    @Test
    void ifDnaStrandsConfigurationIsMutant() throws Exception    {
        mockMvc.perform(post("/mutant/")
                .content(asJsonString(new DnaSequence(dnaCases.get(DNA_STRANDS_CONFIGURATION_MUTANT))))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    @Test
    void ifDnaStrandsConfigurationIsMutantWithAllMatchesInHorizontalStrands() throws Exception    {
        mockMvc.perform(post("/mutant/")
                .content(asJsonString(new DnaSequence(dnaCases.get(DNA_STRANDS_CONFIGURATION_MUTANT_HORIZONTAL))))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    @Test
    void ifDnaStrandsConfigurationIsHuman() throws Exception    {
        mockMvc.perform(post("/mutant/")
                .content(asJsonString(new DnaSequence(dnaCases.get(DNA_STRANDS_CONFIGURATION_HUMAN))))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    void testGetStats() throws Exception {
        mockMvc.perform(get("/stats/")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.ratio").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.count_mutant_dna").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.count_human_dna").exists());
    }

    static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

