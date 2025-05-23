package org.example.animalchipization.dto.account;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Optional;

/**
 * Contains all fields that
 *
 * @author Aleksey
 */
//@Setter
//@Getter
public record AccountSearchCriteria(@NotNull @NotBlank @Size(max = 255) String firstName,
                                    @NotNull @NotBlank @Size(max = 255) String lastName,
                                    @NotNull @NotBlank @Size(max = 255) @Email String email) {
}
