package org.example.animalchipization.service.account;

import org.example.animalchipization.entities.Account;
import org.example.animalchipization.enums.errors.AccountError;
import org.example.animalchipization.exception.entities.AccountException;
import org.example.animalchipization.repository.AccountRepository;
import org.springframework.stereotype.Component;

/**
 * @author Aleksey
 */
@Component
public class AccountValidator {

    private final AccountRepository accountRepository;

    public AccountValidator(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account validateAndGetAccount(Integer accountId) {
        return accountRepository.findById((long) accountId)
                .orElseThrow(() -> new AccountException(AccountError.ACCOUNT_NOT_FOUND));
    }

    public void checkEmailAlreadyExistence(String email) {
        if (accountRepository.findAllByEmail(email).size() == 2) {
            throw new AccountException(AccountError.ACCOUNT_WITH_EMAIL_ALREADY_EXISTS);
        }
    }

    public void checkAccountExistence(Integer accountId) {
        if (!accountRepository.existsById((long) accountId)) {
            throw new AccountException(AccountError.ACCOUNT_NOT_FOUND);
        }
    }
}
