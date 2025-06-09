package org.example.animalchipization.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.animalchipization.dto.account.AccountDtoIn;
import org.example.animalchipization.dto.account.AccountDtoOut;
import org.example.animalchipization.service.account.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Aleksey
 */
@RestController
@Tag(name = "auth")
@RequiredArgsConstructor
public class AuthController {

    private final AccountService accountService;

    @PostMapping("/registration")
    @Validated
    public ResponseEntity<AccountDtoOut> registerAccount(@Validated @RequestBody AccountDtoIn accountDtoIn) {

        var accountDtoOut = accountService.addAccount(accountDtoIn);

        return ResponseEntity.ok(accountDtoOut);
    }
}
