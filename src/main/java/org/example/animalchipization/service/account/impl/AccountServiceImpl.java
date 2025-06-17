package org.example.animalchipization.service.account.impl;

import lombok.RequiredArgsConstructor;
import org.example.animalchipization.dto.account.AccountDtoIn;
import org.example.animalchipization.dto.account.AccountDtoOut;
import org.example.animalchipization.dto.account.AccountSearchCriteria;
import org.example.animalchipization.entity.Account;
import org.example.animalchipization.entity.Account_;
import org.example.animalchipization.enums.error.BadRequestError;
import org.example.animalchipization.exception.RequestException;
import org.example.animalchipization.mapper.AccountMapper;
import org.example.animalchipization.repository.AccountRepository;
import org.example.animalchipization.service.account.AccountService;
import org.example.animalchipization.service.account.AccountValidator;
import org.example.animalchipization.util.UserAuthentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Aleksey
 */
@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountMapper accountMapper;
    private final AccountRepository accountRepository;
    private final AccountValidator accountValidator;


    @Override
    public AccountDtoOut getAccount(Integer accountId) {

        var account = accountValidator.validateAndGetById((long) accountId);

        return accountMapper.toDto(account);
    }

    @Override
    public AccountDtoOut addAccount(AccountDtoIn accountDtoIn) {
        /* Make "/registration" permitAll in security config and uncomment this method call
         * to throw custom error.
         */
//        accountValidator.checkAuthorized();

        accountValidator.checkEmailAlreadyExistenceForAdd(accountDtoIn.getEmail());

        var hash = UserAuthentication.encodeEmailAndPassword(
                accountDtoIn.getEmail(),
                accountDtoIn.getPassword());

        var account = accountMapper.toEntity(accountDtoIn);
        account.setBase64(hash);
        var savedAccount = accountRepository.save(account);

        return accountMapper.toDto(savedAccount);
    }

    @Override
    @Transactional
    public AccountDtoOut updateAccount(Integer accountId, AccountDtoIn accountDtoIn) {

        accountValidator.validateAndAuthenticateAccountById((long) accountId);

        accountValidator.checkEmailAlreadyExistenceForUpdate(accountDtoIn.getEmail());

        var hash = UserAuthentication.encodeEmailAndPassword(
                accountDtoIn.getEmail(),
                accountDtoIn.getPassword());

        var account = accountMapper.toEntity(accountDtoIn);
        account.setAccountId(accountId);
        account.setBase64(hash);
        accountRepository.save(account);

        return accountMapper.toDto(account);
    }

    @Override
    public void deleteAccountById(Integer accountId) {

        accountValidator.validateAndAuthenticateAccountById((long) accountId);

        try {
            accountRepository.deleteById((long) accountId);
        } catch (Exception e) {
            throw new RequestException(BadRequestError.ACCOUNT_STILL_LINKED);
        }
    }

    @Override
    public List<AccountDtoOut> searchAccount(AccountSearchCriteria accountSearchCriteria,
                                             int limit, int offset) {

        List<Account> accountList = accountRepository.searchAccountsByFirstNameAndLastNameAndEmail(
                accountSearchCriteria.firstName(),
                accountSearchCriteria.lastName(),
                accountSearchCriteria.email(),
                limit,
                offset
        );

        return accountList.stream().map(accountMapper::toDto).toList();
    }
}
