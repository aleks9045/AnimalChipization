package org.example.animalchipization.enums.errors;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * @author Aleksey
 */
@Getter
public enum AnimalError {
    ANIMAL_NOT_FOUND("Animal with this id not found", HttpStatus.NOT_FOUND),
    ANIMAL_CHIPPER_NOT_FOUND("Animal chipper not found", HttpStatus.NOT_FOUND),
    ANIMAL_TYPE_NOT_FOUND("Animal type not found", HttpStatus.NOT_FOUND),
    ANIMAL_CHIPPING_LOCATION_NOT_FOUND("Animal chipping location not found", HttpStatus.NOT_FOUND),
    ANIMAL_TYPES_DUPLICATES("Animal types has duplicates", HttpStatus.CONFLICT);


    private final String message;
    private final HttpStatus httpStatus;

    AnimalError(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
