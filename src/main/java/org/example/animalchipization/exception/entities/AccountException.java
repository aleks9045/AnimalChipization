package org.example.animalchipization.exception.entities;

import lombok.Getter;
import org.example.animalchipization.enums.errors.AccountError;
import org.example.animalchipization.exception.EntityException;

/**
 * @author Aleksey
 */
public class AccountException extends EntityException {

    public AccountException(AccountError accountError) {
        super(accountError.getMessage(), accountError.getHttpStatus());
    }
}
