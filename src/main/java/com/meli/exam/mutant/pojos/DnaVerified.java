package com.meli.exam.mutant.pojos;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.*;

@DynamoDBTable(tableName = "DnaVerified")
@Getter
@AllArgsConstructor
public class DnaVerified {

    @DynamoDBHashKey(attributeName = "dna")
    private String dna;

    @DynamoDBAttribute(attributeName = "isMutant")
    private boolean isMutant;

}
