package org.example.animalchipization.dto.account;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.example.animalchipization.entity.Account;

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
public class AccountDtoIn {

    @Schema(description = "first name", maxLength = 255, example = "John", type = "String")
    @NotBlank
    @Size(max = 255)
    private String firstName;

    @Schema(description = "last name", maxLength = 255, example = "Doe", type = "String")
    @NotBlank
    @Size(max = 255)
    private String lastName;

    @Schema(description = "email", maxLength = 255, example = "example@gmail.com", type = "String")
    @NotBlank
    @Size(max = 255)
    @Email
    private String email;

    @Schema(description = "password", maxLength = 255, example = "password", type = "String")
    @NotBlank
    @Size(max = 255)
    private String password;
}
