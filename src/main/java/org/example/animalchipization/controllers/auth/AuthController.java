package org.example.animalchipization.controllers.auth;

import org.example.animalchipization.dto.account.AccountDtoIn;
import org.example.animalchipization.dto.account.AccountDtoOut;
import org.example.animalchipization.service.account.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Aleksey
 */
@RestController
public class AuthController {
    private final AccountService accountService;

    @Autowired
    public AuthController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/registration")
    public ResponseEntity<AccountDtoOut> registerAccount(@Validated @RequestBody AccountDtoIn accountDtoIn) {
        // check
        AccountDtoOut accountDtoOut = accountService.addAccount(accountDtoIn);

        return ResponseEntity.status(HttpStatus.CREATED).body(accountDtoOut);
    }
}
