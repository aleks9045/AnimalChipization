package org.example.animalchipization.enums.errors;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Enumeration for every error with "Forbidden" status
 * @author Aleksey
 */
@Getter
public enum ForbiddenError implements EnumError {
    ACCOUNT_ALREADY_AUTHORIZED("Account already authorized"),
    ACCOUNT_NOT_FOUND("Account not found");

    private final String message;
    private final HttpStatus httpStatus = HttpStatus.FORBIDDEN;

    ForbiddenError(String message) {
        this.message = message;
    }
}
