package org.example.animalchipization.dto.account;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.example.animalchipization.entities.Account;


/**
 * Contains all fields that participates in search process for {@link Account} entity
 *
 * @author Aleksey
 */
public record AccountSearchCriteria(@NotNull @NotBlank @Size(max = 255) String firstName,
                                    @NotNull @NotBlank @Size(max = 255) String lastName,
                                    @NotNull @NotBlank @Size(max = 255) @Email String email) {
}
