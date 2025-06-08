package org.example.animalchipization.service.account.impl;

import org.example.animalchipization.dto.account.AccountDtoIn;
import org.example.animalchipization.dto.account.AccountDtoOut;
import org.example.animalchipization.dto.account.AccountSearchCriteria;
import org.example.animalchipization.entities.Account;
import org.example.animalchipization.entities.Account_;
import org.example.animalchipization.enums.errors.BadRequestError;
import org.example.animalchipization.exception.RequestException;
import org.example.animalchipization.mappers.AccountMapper;
import org.example.animalchipization.repository.AccountRepository;
import org.example.animalchipization.service.JpaSpecificationBuilder;
import org.example.animalchipization.service.account.AccountService;
import org.example.animalchipization.service.account.AccountValidator;
import org.example.animalchipization.service.auth.util.UserAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Aleksey
 */
@Service
public class AccountServiceImpl implements AccountService {
    private final AccountMapper accountMapper;
    private final AccountRepository accountRepository;
    private final AccountValidator accountValidator;


    @Autowired
    public AccountServiceImpl(AccountMapper accountMapper, AccountRepository accountRepository, AccountValidator accountValidator) {
        this.accountMapper = accountMapper;
        this.accountRepository = accountRepository;
        this.accountValidator = accountValidator;
    }

    @Override
    public AccountDtoOut getAccount(Integer accountId) {

        Account account = accountValidator.validateAndGetById((long) accountId);

        return accountMapper.toDto(account);
    }


    @Override
    public AccountDtoOut addAccount(AccountDtoIn accountDtoIn) {
        /* Make "/registration" permitAll in security config and uncomment this method call
         * to throw custom error.
         */
//        accountValidator.checkAuthorized();

        accountValidator.checkEmailAlreadyExistenceForAdd(accountDtoIn.getEmail());

        String hash = UserAuthentication.encodeEmailAndPassword(
                accountDtoIn.getEmail(),
                accountDtoIn.getPassword());

        Account account = accountMapper.toEntity(accountDtoIn);
        account.setBase64(hash);
        Account savedAccount = accountRepository.save(account);

        return accountMapper.toDto(savedAccount);
    }

    @Override
    @Transactional
    public AccountDtoOut updateAccount(Integer accountId, AccountDtoIn accountDtoIn) {

        accountValidator.validateAndAuthenticateAccountById((long) accountId);

        accountValidator.checkEmailAlreadyExistenceForUpdate(accountDtoIn.getEmail());

        String hash = UserAuthentication.encodeEmailAndPassword(
                accountDtoIn.getEmail(),
                accountDtoIn.getPassword());

        Account account = accountMapper.toEntity(accountDtoIn);
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
                                             Pageable pageable) {

        Specification<Account> spec = Specification.where(
                JpaSpecificationBuilder.<Account>likeString(
                        Account_.FIRST_NAME, accountSearchCriteria.firstName())
        ).and(
                JpaSpecificationBuilder.likeString(
                        Account_.LAST_NAME, accountSearchCriteria.lastName())
        ).and(
                JpaSpecificationBuilder.likeString(
                        Account_.EMAIL, accountSearchCriteria.email())
        );

        Page<Account> accountPage = accountRepository.findAll(spec, pageable);

        return accountPage.map(accountMapper::toDto).getContent();
    }
}
