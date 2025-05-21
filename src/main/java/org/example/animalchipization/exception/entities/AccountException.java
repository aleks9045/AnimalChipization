package org.example.animalchipization.exception.entities;

import org.example.animalchipization.enums.errors.AccountError;
import org.example.animalchipization.exception.EntityException;
import org.springframework.http.HttpStatus;

/**
 * @author Aleksey
 */
public class AccountException extends EntityException {

    public AccountException(AccountError accountError, HttpStatus httpStatus) {
        super(accountError.getMessage(), httpStatus);
    }

}
