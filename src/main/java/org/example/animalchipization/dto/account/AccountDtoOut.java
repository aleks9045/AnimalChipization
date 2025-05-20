package org.example.animalchipization.dto.account;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Aleksey
 */
@AllArgsConstructor
@Setter
@Getter
public class AccountDtoOut {

    @NotNull @NotBlank
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
