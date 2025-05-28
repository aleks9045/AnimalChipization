package org.example.animalchipization.enums.errors;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * @author Aleksey
 */
@Getter
public enum AccountError {
    ACCOUNT_NOT_FOUND("Account not found", HttpStatus.NOT_FOUND),
    ACCOUNT_WITH_EMAIL_ALREADY_EXISTS("Account with this email already exists", HttpStatus.CONFLICT);

    private final String message;
    private final HttpStatus httpStatus;

    AccountError(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
