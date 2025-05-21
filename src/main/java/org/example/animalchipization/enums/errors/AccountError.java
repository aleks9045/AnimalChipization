package org.example.animalchipization.enums.errors;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * @author Aleksey
 */
@Getter
public enum AccountError {
    ACCOUNT_NOT_FOUND("Account with this id not found", HttpStatus.NOT_FOUND);

    private final String message;
    private final HttpStatus httpStatus;

    AccountError(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
