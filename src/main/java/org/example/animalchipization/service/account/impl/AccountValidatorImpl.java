package org.example.animalchipization.service.account.impl;

import lombok.RequiredArgsConstructor;
import org.example.animalchipization.entity.Account;
import org.example.animalchipization.enums.error.ConflictError;
import org.example.animalchipization.enums.error.ForbiddenError;
import org.example.animalchipization.enums.error.NotFoundError;
import org.example.animalchipization.exception.RequestException;
import org.example.animalchipization.repository.AccountRepository;
import org.example.animalchipization.security.provider.CustomAuthProvider;
import org.example.animalchipization.service.account.AccountValidator;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.apache.commons.lang3.BooleanUtils.isFalse;

/**
 * @author Aleksey
 */
@Component
@RequiredArgsConstructor
public class AccountValidatorImpl implements AccountValidator {

    private final AccountRepository accountRepository;


    @Override
    public void authenticateAccount(Account account) {
        if (isFalse(account.getEmail().equals(CustomAuthProvider.getCurrentUserEmail()))) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
    }

    @Override
    public void validateAndAuthenticateAccountById(Long accountId) {
        var account = accountRepository.findById((long) accountId)
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
        if (isFalse(accountRepository.existsById(accountId))) {
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
        var account = this.getByEmail(email);
        this.authenticateAccount(account);
        return account;
    }

    @Override
    public void checkAuthorized() {
        var auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null && auth.isAuthenticated() &&
                isFalse((auth instanceof AnonymousAuthenticationToken))) {
            throw new RequestException(ForbiddenError.ACCOUNT_ALREADY_AUTHORIZED);
        }
    }

    @Override
    public void checkEmailAlreadyExistenceForAdd(String email) {
        var account = accountRepository.findByEmail(email);

        if (account.isPresent()) {
            throw new RequestException(ConflictError.ACCOUNT_WITH_EMAIL_ALREADY_EXISTS);
        }
    }

    @Override
    public void checkEmailAlreadyExistenceForUpdate(String email) {
        var account = accountRepository.findByEmail(email);

        if (account.isPresent() && isFalse(account.get().getEmail().equals(email))) {
            throw new RequestException(ConflictError.ACCOUNT_WITH_EMAIL_ALREADY_EXISTS);
        }
    }
}
