package org.example.animalchipization.enums.errors;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * @author Aleksey
 */
@Getter
public enum VisitedLocationError {
    VISITED_LOCATION_NOT_FOUND("Visited location not found",HttpStatus.NOT_FOUND),
    VISITED_LOCATION_ALREADY_EXISTS("Visited location already exists", HttpStatus.CONFLICT),
    VISITED_LOCATION_EQUALS_LOCATION("Location field in visited location equals given location", HttpStatus.BAD_REQUEST);

    private final String message;
    private final HttpStatus httpStatus;

    VisitedLocationError(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
