package org.example.animalchipization.enums.errors;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * @author Aleksey
 */
@Getter
public enum LocationError {
    LOCATION_NOT_FOUND("Location not found", HttpStatus.NOT_FOUND),
    LOCATION_ALREADY_EXISTS("Location already exists", HttpStatus.CONFLICT),
    LOCATION_STILL_LINKED("Location still linked", HttpStatus.BAD_REQUEST);

    private final String message;
    private final HttpStatus httpStatus;

    LocationError(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
