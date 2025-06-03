package org.example.animalchipization.service.animalType;

import org.example.animalchipization.entities.AnimalType;
import org.example.animalchipization.enums.errors.BadRequestError;
import org.example.animalchipization.enums.errors.ConflictError;
import org.example.animalchipization.enums.errors.NotFoundError;
import org.example.animalchipization.exception.RequestException;
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
