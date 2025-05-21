package org.example.animalchipization.service.account.impl;

import org.example.animalchipization.dto.account.AccountDtoIn;
import org.example.animalchipization.dto.account.AccountDtoOut;
import org.example.animalchipization.entities.Account;
import org.example.animalchipization.enums.errors.AccountError;
import org.example.animalchipization.exception.entities.AccountException;
import org.example.animalchipization.mappers.AccountMapper;
import org.example.animalchipization.repository.AccountRepository;
import org.example.animalchipization.service.account.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public AccountDtoOut addAccount(AccountDtoIn accountDtoIn) {

        Account account = accountMapper.toEntity(accountDtoIn);
        Account savedAccount = accountRepository.save(account);

        return accountMapper.toDto(savedAccount);
    }

    @Override
    public AccountDtoOut updateAccount(Integer accountId, AccountDtoIn accountDtoIn) {

        Account account = accountMapper.toEntity(accountDtoIn);
        account.setAccountId(accountId);
        Account savedAccount = accountRepository.save(account);

        return accountMapper.toDto(savedAccount);
    }

    @Override
    public void deleteAccountById(Integer accountId) {
        accountRepository.deleteById((long) accountId);
    }
}
