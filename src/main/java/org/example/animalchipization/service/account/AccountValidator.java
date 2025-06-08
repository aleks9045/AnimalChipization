package org.example.animalchipization.service.account;

import org.example.animalchipization.entities.Account;
import org.example.animalchipization.service.Validator;

import java.util.Optional;

/**
 * @author Aleksey
 */
public interface AccountValidator extends Validator<Account> {

    Account validateAndGetByEmail(String email);

    Account getByEmail(String email);

    void validateAndAuthenticateAccountById(Long accountId);

    void authenticateAccount(Account account);

    void checkAuthorized();

    void checkEmailAlreadyExistenceForAdd(String email);

    void checkEmailAlreadyExistenceForUpdate(String email);
}
