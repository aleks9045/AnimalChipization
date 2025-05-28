package org.example.animalchipization.enums.errors;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * @author Aleksey
 */
@Getter
public enum AnimalTypeError {
    ANIMAL_TYPE_NOT_FOUND("Animal type not found", HttpStatus.NOT_FOUND),
    ANIMAL_TYPE_ALREADY_EXISTS("Animal type already exists", HttpStatus.CONFLICT);

    private final String message;
    private final HttpStatus httpStatus;

    AnimalTypeError(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
