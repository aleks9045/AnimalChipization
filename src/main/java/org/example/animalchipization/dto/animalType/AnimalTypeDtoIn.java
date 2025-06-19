package org.example.animalchipization.dto.animalType;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.example.animalchipization.entity.AnimalType;

/**
 * Represents animal type data transfer object
 *
 * @author Aleksey
 * @see AnimalType Animal type entity
 */
@AllArgsConstructor
@Setter
@Getter
@ToString
public class AnimalTypeDtoIn {

    @Schema(description = "animal type", example = "big")
    @NotBlank
    @Size(max = 255)
    private String type;
}
