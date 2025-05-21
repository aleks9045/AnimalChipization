package org.example.animalchipization.controllers.account;

import lombok.AllArgsConstructor;
import org.example.animalchipization.dto.account.AccountDtoIn;
import org.example.animalchipization.dto.account.AccountDtoOut;
import org.example.animalchipization.entities.Account;
import org.example.animalchipization.service.account.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author Aleksey
 */
@RestController
@RequestMapping("account")
public class AccountController {
    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<AccountDtoOut> getAccountById(@PathVariable Integer accountId) {

        AccountDtoOut accountDtoOut = accountService.getAccount(accountId);

        return ResponseEntity.status(HttpStatus.OK).body(accountDtoOut);
    }


    @PutMapping("/{accountId}")
    public ResponseEntity<AccountDtoOut> updateAccountById(@PathVariable Integer accountId,
                                                           @Validated @RequestBody AccountDtoIn accountDtoIn) {

        AccountDtoOut accountDtoOut = accountService.updateAccount(accountId, accountDtoIn);

        return ResponseEntity.status(HttpStatus.OK).body(accountDtoOut);
    }

    @DeleteMapping("/{accountId}")
    public void deleteAccountById(@PathVariable Integer accountId) {
        accountService.deleteAccountById(accountId);
    }

}
