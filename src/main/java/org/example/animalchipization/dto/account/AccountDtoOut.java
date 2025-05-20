package org.example.animalchipization.dto.account;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.example.animalchipization.entities.Account;

/**
 * Represents account data transfer object
 *
 * <p>Used to display output data in responses<br>
 * All fields must be not null or blank<br>
 *
 * @see Account Account entity
 * @author Aleksey
 */
@AllArgsConstructor
@Setter
@Getter
public class AccountDtoOut {

    @NotNull
    @Positive
    private Integer id;

    @NotNull @NotBlank
    @Size(max = 255)
    private String firstName;

    @NotNull @NotBlank
    @Size(max = 255)
    private String lastName;

    @NotNull @NotBlank
    @Size(max = 255)
    @Email
    private String email;
}
