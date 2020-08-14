package org.sample;

import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class AnimalService extends AbstractService{

    @Inject
    DynamoDbClient dynamoDB;

    public List<Animal> findAll() {
        return dynamoDB.scanPaginator(scanRequest()).items().stream()
                .map(Animal::from)
                .collect(Collectors.toList());
    }

    public List<Animal> add(Animal animal) {
        dynamoDB.putItem(putRequest(animal));
        return findAll();
    }

    public Animal get(String name) {
        Animal animal = Animal.from(dynamoDB.getItem(getRequest(name)).item());
        if (!animal.getDeleted())
            return animal;
        return new Animal();
    }

    public List<Animal> update(String name, Animal animal) {
        dynamoDB.updateItem(updateRequest(name, animal));
        return findAll();
    }

    public List<Animal> delete(String name) {
        Animal animal = Animal.from(dynamoDB.getItem(getRequest(name)).item());
        if (animal.getName() != null){
            animal.setDeleted(true);
            dynamoDB.updateItem(updateRequest(name, animal));
        }
        return findAll();
    }
}
