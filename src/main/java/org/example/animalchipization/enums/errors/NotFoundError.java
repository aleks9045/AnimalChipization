package org.example.animalchipization.enums.errors;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Aleksey
 */
@Getter
public enum NotFoundError implements EnumError {

    ACCOUNT_NOT_FOUND("Account not found"),
    ANIMAL_NOT_FOUND("Animal with not found"),
    ANIMAL_CHIPPER_NOT_FOUND("Animal chipper not found"),
    ANIMAL_CHIPPING_LOCATION_NOT_FOUND("Animal chipping location not found"),
    ANIMAL_TYPE_NOT_FOUND("Animal type not found"),
    LOCATION_NOT_FOUND("Location not found"),
    VISITED_LOCATION_NOT_FOUND("Visited location not found");

    private final String message;
    private final HttpStatus httpStatus = HttpStatus.NOT_FOUND;

    NotFoundError(String message) {
        this.message = message;
    }
}
