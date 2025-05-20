package org.example.animalchipization.dto.animalType;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.example.animalchipization.models.AnimalType;

/**
 * Represents animal type data transfer object
 *
 * <p>Used to validate input data in requests via jakarta validation<br>
 * All fields must be not null or blank<br>
 * Includes schemas definitions for display in swagger
 *
 * @see AnimalType Animal type entity
 * @author Aleksey
 */
@AllArgsConstructor
@Setter
@Getter
public class AnimalTypeDtoIn {

    @Schema(description = "animal type", example = "big")
    @NotNull @NotBlank
    @Size(max = 255)
    private String type;
}
