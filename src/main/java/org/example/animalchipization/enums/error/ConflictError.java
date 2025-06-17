package org.example.animalchipization.enums.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Enumeration for every error with "Conflict" status
 *
 * @author Aleksey
 */
@Getter
public enum ConflictError implements EnumError {

    ACCOUNT_WITH_EMAIL_ALREADY_EXISTS("Account with this email already exists"),
    ANIMAL_TYPES_DUPLICATES("Animal types has duplicates"),
    ANIMAL_TYPE_ALREADY_EXISTS("Animal type already exists"),
    LOCATION_ALREADY_EXISTS("Location already exists"),
    VISITED_LOCATION_ALREADY_EXISTS("Visited location already exists");

    private final String message;
    private final HttpStatus httpStatus = HttpStatus.CONFLICT;

    ConflictError(String message) {
        this.message = message;
    }
}
