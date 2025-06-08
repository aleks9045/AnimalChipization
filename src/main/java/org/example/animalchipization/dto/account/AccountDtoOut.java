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
 * <p>Used to display output data in responses<br><br>
 * All fields must be not null or blank<br>
 * Id field must be strictly greater than 0<br>
 * Validates email field
 *
 * @see Account Account entity
 * @author Aleksey
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
