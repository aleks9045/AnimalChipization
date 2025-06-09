package org.example.animalchipization.dto.account;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.example.animalchipization.entities.Account;

/**
 * Represents account data transfer object
 *
 * @author Aleksey
 * @see Account Account entity
 */
@AllArgsConstructor
@Setter
@Getter
@ToString
public class AccountDtoOut {

    @NotNull
    @Positive
    @Min(0)
    private Integer id;

    @NotBlank
    @Size(max = 255)
    private String firstName;

    @NotBlank
    @Size(max = 255)
    private String lastName;

    @NotBlank
    @Size(max = 255)
    @Email
    private String email;
}
