package org.example.animalchipization.exception.entities;

import org.example.animalchipization.entities.VisitedLocation;
import org.example.animalchipization.enums.errors.LocationError;
import org.example.animalchipization.enums.errors.VisitedLocationError;
import org.example.animalchipization.exception.EntityException;

/**
 * @author Aleksey
 */
public class VisitedLocationException extends EntityException {

    public VisitedLocationException(VisitedLocationError visitedLocationError) {
        super(visitedLocationError.getMessage(), visitedLocationError.getHttpStatus());
    }
}
