package org.sample;

import io.quarkus.runtime.annotations.RegisterForReflection;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.util.Map;
import java.util.Objects;

@RegisterForReflection
public class Animal {
    private String name;
    private String classification;

    public Animal(){};

    public static Animal from(Map<String, AttributeValue> item) {
        Animal animal = new Animal();
        if (item != null && !item.isEmpty()) {
            animal.setName(item.get(AbstractService.ANIMAL_NAME_COL).s());
            animal.setClassification(item.get(AbstractService.ANIMAL_CLASS_COL).s());
        }
        return animal;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Animal)) {
            return false;
        }

        Animal other = (Animal) obj;

        return Objects.equals(other.name, this.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.name);
    }
}
