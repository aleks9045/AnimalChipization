package org.example.animalchipization.service.account.impl;

import org.example.animalchipization.dto.account.AccountDtoIn;
import org.example.animalchipization.dto.account.AccountDtoOut;
import org.example.animalchipization.dto.account.AccountSearchCriteria;
import org.example.animalchipization.entities.Account;
import org.example.animalchipization.enums.errors.AccountError;
import org.example.animalchipization.exception.entities.AccountException;
import org.example.animalchipization.mappers.AccountMapper;
import org.example.animalchipization.repository.AccountRepository;
import org.example.animalchipization.service.JpaSpecificationBuilder;
import org.example.animalchipization.service.account.AccountService;
import org.example.animalchipization.service.auth.UserAuthentication;
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


    @Autowired
    public AccountServiceImpl(AccountMapper accountMapper, AccountRepository accountRepository) {
        this.accountMapper = accountMapper;
        this.accountRepository = accountRepository;
    }

    @Override
    public AccountDtoOut getAccount(Integer accountId) {

        Account account = accountRepository.findById((long) accountId)
                .orElseThrow(() -> new AccountException(AccountError.ACCOUNT_NOT_FOUND));

        return accountMapper.toDto(account);
    }


    @Override
    @Transactional
    public AccountDtoOut addAccount(AccountDtoIn accountDtoIn) {
        if (accountRepository.existsAccountByEmail(accountDtoIn.getEmail())) {
            throw new AccountException(AccountError.ACCOUNT_WITH_EMAIL_ALREADY_EXISTS);
        }

        String hash = UserAuthentication.hashEmailAndPassword(
                accountDtoIn.getEmail(),
                accountDtoIn.getPassword());

        Account account = accountMapper.toEntity(accountDtoIn);
        account.setHash(hash);
        accountRepository.save(account);

        return accountMapper.toDto(account);
    }

    @Override
    @Transactional
    public AccountDtoOut updateAccount(Integer accountId, AccountDtoIn accountDtoIn) {
        if (accountRepository.existsAccountByEmail(accountDtoIn.getEmail())) {
            throw new AccountException(AccountError.ACCOUNT_WITH_EMAIL_ALREADY_EXISTS);
        }

        String hash = UserAuthentication.hashEmailAndPassword(
                accountDtoIn.getEmail(),
                accountDtoIn.getPassword());

        Account account = accountMapper.toEntity(accountDtoIn);
        account.setAccountId(accountId);
        account.setHash(hash);
        accountRepository.save(account);

        return accountMapper.toDto(account);
    }

    @Override
    public void deleteAccountById(Integer accountId) {

        accountRepository.findById((long) accountId)
                .orElseThrow(() -> new AccountException(AccountError.ACCOUNT_NOT_FOUND));

        accountRepository.deleteById((long) accountId);
    }

    @Override
    public List<AccountDtoOut> searchAccount(AccountSearchCriteria accountSearchCriteria,
                                             Pageable pageable) {

        Specification<Account> spec = Specification.where(
                JpaSpecificationBuilder.<Account>likeString("firstName", accountSearchCriteria.firstName())
        ).and(
                JpaSpecificationBuilder.likeString("lastName", accountSearchCriteria.lastName())
        ).and(
                JpaSpecificationBuilder.likeString("email", accountSearchCriteria.email())
        );

        Page<Account> accountPage = accountRepository.findAll(spec, pageable);

        return accountPage.map(accountMapper::toDto).getContent();
    }
}
