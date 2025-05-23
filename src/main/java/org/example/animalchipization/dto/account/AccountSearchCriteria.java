package org.example.animalchipization.dto.account;

import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.example.animalchipization.entities.Account;


/**
 * Contains all fields that participates in search process for {@link Account} entity
 *
 * @author Aleksey
 */
public record AccountSearchCriteria(@Nullable @Size(max = 255) String firstName,
                                    @Nullable @Size(max = 255) String lastName,
                                    @Nullable @Size(max = 255) @Email String email) {}
