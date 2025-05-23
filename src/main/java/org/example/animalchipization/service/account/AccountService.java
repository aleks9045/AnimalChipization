package org.example.animalchipization.service.account;

import org.example.animalchipization.dto.account.AccountDtoIn;
import org.example.animalchipization.dto.account.AccountDtoOut;
import org.example.animalchipization.dto.account.AccountSearchCriteria;
import org.example.animalchipization.entities.Account;
import org.example.animalchipization.service.account.impl.AccountServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 *  A set of methods for business logic with {@link Account} entity
 *
 *  @author Aleksey
 *
 *  @see AccountServiceImpl Implementation of this interface
 */

public interface AccountService {
    /**
     * Gets account entry from database
     *
     * @param accountId account id
     * @return {@link AccountDtoOut}
     */
    AccountDtoOut getAccount(Integer accountId);


    /**
     * Adds account entry from database
     *
     * @param accountDtoIn account schema {@link AccountDtoIn}
     */
    AccountDtoOut addAccount(AccountDtoIn accountDtoIn);

    /**
     * Updates account entry in database
     *
     * @param accountId account id
     * @param accountDtoIn {@link AccountDtoIn}
     * @return {@link AccountDtoOut}
     */
    AccountDtoOut updateAccount(Integer accountId, AccountDtoIn accountDtoIn);

    /**
     * Deletes account entry from database
     *
     * @param accountId account id
     */
    void deleteAccountById(Integer accountId);

    /**
     * Search accounts entries from database
     *
     * @param accountSearchCriteria account search criteria DTO
     * @param pageable Pageable object
     * @return List of Account DTO for output
     */
    List<AccountDtoOut> searchAccount(AccountSearchCriteria accountSearchCriteria,
                                      Pageable pageable);

}
