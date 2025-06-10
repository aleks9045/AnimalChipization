package org.example.animalchipization.service.animalType;

import org.example.animalchipization.entity.AnimalType;
import org.example.animalchipization.service.Validator;

import java.util.Set;

/**
 * @author Aleksey
 */
public interface AnimalTypeValidator extends Validator<AnimalType> {

    void checkExistence(String type);

    void validateTypes(Set<AnimalType> animalTypeSet, AnimalType animalType);

    void checkTypesDuplicatesType(Set<AnimalType> animalTypeSet, AnimalType animalType);

    void checkLinked(AnimalType animalType);
}
