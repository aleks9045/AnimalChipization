package org.example.animalchipization.enums.errors;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * @author Aleksey
 */
@Getter
public enum VisitedLocationError {
    VISITED_LOCATION_NOT_FOUND("Visited location with this id not found",HttpStatus.NOT_FOUND),
    VISITED_LOCATION_ALREADY_EXISTS("Visited location with this data already exists", HttpStatus.CONFLICT);

    private final String message;
    private final HttpStatus httpStatus;

    VisitedLocationError(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
