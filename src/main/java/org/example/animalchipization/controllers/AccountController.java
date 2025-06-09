package org.example.animalchipization.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.example.animalchipization.dto.account.AccountDtoIn;
import org.example.animalchipization.dto.account.AccountDtoOut;
import org.example.animalchipization.dto.account.AccountSearchCriteria;
import org.example.animalchipization.service.account.AccountService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Aleksey
 */
@RestController
@RequestMapping("accounts")
@Tag(name = "accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;


    @GetMapping("/{accountId}")
    @Validated
    public ResponseEntity<AccountDtoOut> getAccountById(@PathVariable @Positive @Min(1) Integer accountId) {

        var accountDtoOut = accountService.getAccount(accountId);

        return ResponseEntity.ok(accountDtoOut);
    }

    @PutMapping("/{accountId}")
    @Validated
    public ResponseEntity<AccountDtoOut> updateAccountById(
            @PathVariable @Positive @Min(1) Integer accountId,
            @Validated @RequestBody AccountDtoIn accountDtoIn) {

        var accountDtoOut = accountService.updateAccount(accountId, accountDtoIn);

        return ResponseEntity.ok(accountDtoOut);
    }

    @DeleteMapping("/{accountId}")
    @ResponseStatus(HttpStatus.OK)
    @Validated
    public void deleteAccountById(@PathVariable @Positive @Min(1) Integer accountId) {
        accountService.deleteAccountById(accountId);
    }

    @GetMapping("/search")
    public ResponseEntity<List<AccountDtoOut>> searchAccounts(
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String email,
            @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
            @RequestParam(defaultValue = "10") @Positive @Min(1) Integer size) {

        var accountSearchCriteria = new AccountSearchCriteria(
                firstName,
                lastName,
                email
        );

        var accountDtoOutList = accountService.searchAccount(
                accountSearchCriteria,
                PageRequest.of(from, size, Sort.by("accountId"))
        );

        return ResponseEntity.ok(accountDtoOutList);
    }
}
