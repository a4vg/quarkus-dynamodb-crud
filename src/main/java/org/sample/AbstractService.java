package org.sample;

import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.GetItemRequest;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;
import software.amazon.awssdk.services.dynamodb.model.ScanRequest;

import java.util.HashMap;
import java.util.Map;

public class AbstractService {
    public final static String ANIMAL_NAME_COL = "animalName";
    public final static String ANIMAL_CLASS_COL = "animalClassification";

    public String getTableName() {
        return "Animals";
    }


    protected ScanRequest scanRequest() {
        return ScanRequest.builder().tableName(getTableName())
                .attributesToGet(ANIMAL_NAME_COL, ANIMAL_CLASS_COL).build();
    }

    protected PutItemRequest putRequest(Animal animal) {
        Map<String, AttributeValue> item = new HashMap<>();

        item.put(ANIMAL_NAME_COL, AttributeValue.builder().s(animal.getName()).build());
        item.put(ANIMAL_CLASS_COL, AttributeValue.builder().s(animal.getClassification()).build());

        return PutItemRequest.builder()
                .tableName(getTableName())
                .item(item)
                .build();
    }

    protected GetItemRequest getRequest(String name) {
        Map<String, AttributeValue> key = new HashMap<>();
        key.put(ANIMAL_NAME_COL, AttributeValue.builder().s(name).build());

        return GetItemRequest.builder()
                .tableName(getTableName())
                .key(key)
                .attributesToGet(ANIMAL_NAME_COL, ANIMAL_CLASS_COL)
                .build();
    }

}
