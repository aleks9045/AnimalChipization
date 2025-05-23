package org.example.animalchipization.enums.errors;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * @author Aleksey
 */
@Getter
public enum AnimalError {
    ANIMAL_NOT_FOUND("Animal with this id not found", HttpStatus.NOT_FOUND),
    ANIMAL_ALREADY_EXISTS("Animal with this data already exists", HttpStatus.CONFLICT);

    private final String message;
    private final HttpStatus httpStatus;

    AnimalError(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
