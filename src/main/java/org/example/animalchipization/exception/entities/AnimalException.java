package org.example.animalchipization.exception.entities;

import org.example.animalchipization.enums.errors.AnimalError;
import org.example.animalchipization.exception.EntityException;
import org.springframework.http.HttpStatus;

/**
 * @author Aleksey
 */
public class AnimalException extends EntityException {

    public AnimalException(AnimalError animalError) {
        super(animalError.getMessage(), animalError.getHttpStatus());
    }
}
