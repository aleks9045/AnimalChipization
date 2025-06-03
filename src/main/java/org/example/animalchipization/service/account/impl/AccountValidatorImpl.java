package org.example.animalchipization.service.account.impl;

import org.example.animalchipization.entities.Account;
import org.example.animalchipization.enums.errors.ConflictError;
import org.example.animalchipization.enums.errors.ForbiddenError;
import org.example.animalchipization.enums.errors.NotFoundError;
import org.example.animalchipization.exception.RequestException;
import org.example.animalchipization.repository.AccountRepository;
import org.example.animalchipization.security.provider.CustomAuthProvider;
import org.example.animalchipization.service.Validator;
import org.example.animalchipization.service.account.AccountValidator;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

/**
 * @author Aleksey
 */
@Component
public class AccountValidatorImpl implements AccountValidator {

    private final AccountRepository accountRepository;

    public AccountValidatorImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public void authenticateAccount(Account account) {
        if (!account.getEmail().equals(CustomAuthProvider.getCurrentUserEmail())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
    }

    @Override
    public void validateAndAuthenticateAccountById(Long accountId) {
        Account account = accountRepository.findById((long) accountId)
                .orElseThrow(() -> new RequestException(ForbiddenError.ACCOUNT_NOT_FOUND));

        this.authenticateAccount(account);
    }

    @Override
    public Account validateAndGetById(Long accountId) {
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new RequestException(NotFoundError.ACCOUNT_NOT_FOUND));
    }

    @Override
    public void checkExistence(Long accountId) {
        if (!accountRepository.existsById(accountId)) {
            throw new RequestException(ForbiddenError.ACCOUNT_NOT_FOUND);
        }
    }

    @Override
    public Account getByEmail(String email) {
        return accountRepository.findByEmail(email)
                .orElseThrow(() -> new RequestException(NotFoundError.ACCOUNT_NOT_FOUND));
    }

    @Override
    public Account validateAndGetByEmail(String email) {
        Account account = this.getByEmail(email);
        this.authenticateAccount(account);
        return account;
    }

    @Override
    public void checkAuthorized() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() &&
                !(auth instanceof AnonymousAuthenticationToken)) {
            throw new RequestException(ForbiddenError.ACCOUNT_ALREADY_AUTHORIZED);
        }
    }

    @Override
    public void checkEmailAlreadyExistenceForAdd(String email) {
        Optional<Account> account = accountRepository.findByEmail(email);
        if (account.isPresent()) {
            throw new RequestException(ConflictError.ACCOUNT_WITH_EMAIL_ALREADY_EXISTS);
        }
    }

    @Override
    public void checkEmailAlreadyExistenceForUpdate(String email) {
        Optional<Account> account = accountRepository.findByEmail(email);
        if (account.isPresent() && !account.get().getEmail().equals(email)) {
            throw new RequestException(ConflictError.ACCOUNT_WITH_EMAIL_ALREADY_EXISTS);
        }
    }
}
