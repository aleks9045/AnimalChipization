package org.example.animalchipization.exception.entities;

import org.example.animalchipization.enums.errors.LocationError;
import org.example.animalchipization.exception.EntityException;

/**
 * @author Aleksey
 */
public class LocationException extends EntityException {

    public LocationException(LocationError locationError) {
        super(locationError.getMessage(), locationError.getHttpStatus());
    }
}
