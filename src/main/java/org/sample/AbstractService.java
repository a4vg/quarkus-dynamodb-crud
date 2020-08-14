package org.sample;

import software.amazon.awssdk.services.dynamodb.model.*;

import java.util.HashMap;
import java.util.Map;

public class AbstractService {
    public final static String ANIMAL_NAME_COL = "animalName";
    public final static String ANIMAL_CLASS_COL = "animalClassification";
    public final static String DELETED_COL = "deleted";


    public String getTableName() {
        return "Animals";
    }

    protected ScanRequest scanRequest() {
        return ScanRequest.builder().tableName(getTableName())
                .filterExpression("attribute_not_exists(" + DELETED_COL + ")")
                .build();
    }

    protected PutItemRequest putRequest(Animal animal) {
        Map<String, AttributeValue> item = new HashMap<>();

        item.put(ANIMAL_NAME_COL, AttributeValue.builder().s(animal.getName()).build());
        item.put(ANIMAL_CLASS_COL, AttributeValue.builder().s(animal.getClassification()).build());
        if (animal.getDeleted() != null)
            item.put(DELETED_COL, AttributeValue.builder().bool(true).build());

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
                .build();
    }

    protected UpdateItemRequest updateRequest(String name, Animal animal) {
        Map<String, AttributeValueUpdate> updates = new HashMap<>();
        updates.put(ANIMAL_CLASS_COL, AttributeValueUpdate.builder().value(AttributeValue.builder().s(animal.getClassification()).build()).build());
        if (animal.getDeleted() != null)
            updates.put(DELETED_COL, AttributeValueUpdate.builder().value(AttributeValue.builder().bool(true).build()).build());

        Map<String, AttributeValue> key = new HashMap<>();
        key.put(ANIMAL_NAME_COL, AttributeValue.builder().s(name).build());

        return UpdateItemRequest.builder()
                .tableName(getTableName())
                .key(key)
                .attributeUpdates(updates)
                .build();
    }
}
