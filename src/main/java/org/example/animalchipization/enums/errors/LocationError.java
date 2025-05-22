package org.example.animalchipization.enums.errors;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * @author Aleksey
 */
@Getter
public enum LocationError {
    LOCATION_NOT_FOUND("Location with this id not found", HttpStatus.NOT_FOUND),
    LOCATION_ALREADY_EXISTS("Location with this data already exists", HttpStatus.CONFLICT);

    private final String message;
    private final HttpStatus httpStatus;

    LocationError(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
