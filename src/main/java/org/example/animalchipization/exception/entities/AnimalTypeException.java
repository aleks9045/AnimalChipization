package org.example.animalchipization.exception.entities;

import org.example.animalchipization.entities.AnimalType;
import org.example.animalchipization.enums.errors.AnimalTypeError;
import org.example.animalchipization.enums.errors.LocationError;
import org.example.animalchipization.exception.EntityException;

/**
 * @author Aleksey
 */
public class AnimalTypeException extends EntityException {

    public AnimalTypeException(AnimalTypeError animalTypeError) {
        super(animalTypeError.getMessage(), animalTypeError.getHttpStatus());
    }
}
