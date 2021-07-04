package com.meli.exam.mutant.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.meli.exam.mutant.pojos.DnaVerified;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class DnaVerifiedDao {

    private final DynamoDBMapper dynamoDBMapper;

    DynamoDBScanExpression scanExpression = new DynamoDBScanExpression().withFilterExpression("mutant = :val1");

    /**
     * Initialize dynamoDBMapper
     *
     * @param dynamoDBMapper
     */
    @Autowired
    public DnaVerifiedDao(DynamoDBMapper dynamoDBMapper) {
        this.dynamoDBMapper = dynamoDBMapper;
    }

    /**
     * Use the scanExpression to retrieve the amount of mutants or humans DNA in table DnaVerified
     * flagMutant= 1 is Mutant
     * flagMutant= 0 is Human
     *
     * @param flagMutant
     * @return the number of dna verified according to FlagMutant
     */
    public int getCountDnaVerified(int flagMutant) {
        Map<String, AttributeValue> valueHashMap = new HashMap<>();
        valueHashMap.put(":val1", new AttributeValue().withN(String.valueOf(flagMutant)));
        scanExpression.withExpressionAttributeValues(valueHashMap);
        return dynamoDBMapper.count(DnaVerified.class, scanExpression);
    }

    /**
     * Create an item in table DnaVerified
     *
     * @param dnaVerified
     */
    public void createDnaVerified(DnaVerified dnaVerified) {
        dynamoDBMapper.save(dnaVerified);
    }
}