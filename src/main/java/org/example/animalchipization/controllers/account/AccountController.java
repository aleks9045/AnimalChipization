package org.example.animalchipization.controllers.account;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import org.example.animalchipization.dto.account.AccountDtoIn;
import org.example.animalchipization.dto.account.AccountDtoOut;
import org.example.animalchipization.dto.account.AccountSearchCriteria;
import org.example.animalchipization.service.account.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * @author Aleksey
 */
@RestController
@RequestMapping("accounts")
public class AccountController {
    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/{accountId}")
    @Validated
    public ResponseEntity<AccountDtoOut> getAccountById(@PathVariable @Positive @Min(1) Integer accountId) {

        AccountDtoOut accountDtoOut = accountService.getAccount(accountId);

        return ResponseEntity.status(HttpStatus.OK).body(accountDtoOut);
    }


    @PutMapping("/{accountId}")
    @Validated
    public ResponseEntity<AccountDtoOut> updateAccountById(@PathVariable @Positive @Min(1) Integer accountId,
                                                           @Validated @RequestBody AccountDtoIn accountDtoIn) {

        AccountDtoOut accountDtoOut = accountService.updateAccount(accountId, accountDtoIn);

        return ResponseEntity.status(HttpStatus.OK).body(accountDtoOut);
    }

    @DeleteMapping("/{accountId}")
    @Validated
    public void deleteAccountById(@PathVariable @Positive @Min(1) Integer accountId) {
        accountService.deleteAccountById(accountId);
    }

    @GetMapping("/search")
    public ResponseEntity<List<AccountDtoOut>> searchAccounts(
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String email,
            @RequestParam(defaultValue = "0") Integer from,
            @RequestParam(defaultValue = "10") Integer size) {

        AccountSearchCriteria accountSearchCriteria = new AccountSearchCriteria(
                firstName,
                lastName,
                email
        );

        List<AccountDtoOut> accountDtoOutList = accountService.searchAccount(accountSearchCriteria,
                PageRequest.of(from, size));

        return ResponseEntity.status(HttpStatus.OK).body(accountDtoOutList);
    }

}
