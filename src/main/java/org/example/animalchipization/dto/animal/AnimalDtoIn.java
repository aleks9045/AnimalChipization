package org.example.animalchipization.dto.animal;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.example.animalchipization.enums.AnimalGender;

import org.example.animalchipization.entities.Animal;
import org.example.animalchipization.validation.annotation.NoDuplicates;

import java.util.List;
import java.util.Set;

/**
 * Represents animal data transfer object
 *
 * <p>Used to validate input data in requests via jakarta validation<br>
 * All fields must be not null or blank<br>
 * Includes schemas definitions for display in swagger
 *
 * @see Animal Animal entity
 * @author Aleksey
 */
@AllArgsConstructor
@Setter
@Getter
@ToString
public class AnimalDtoIn {

    @Schema(description = "animal weight", example = "4.4")
    @NotNull
    @Positive
    private Float weight;

    @Schema(description = "animal length", example = "0.8")
    @NotNull
    @Positive
    private Float length;

    @Schema(description = "animal height", example = "0.4")
    @NotNull
    @Positive
    private Float height;

    @Schema(description = "animal gender", example = "MALE")
    @NotNull
    private AnimalGender gender;

    @Schema(description = "animal chipper id", example = "1")
    @NotNull
    private Integer chipperId;

    @Schema(description = "animal chipping location id", example = "1")
    @NotNull
    private Long chippingLocationId;

    @Schema(description = "animal types", example = "[1]")
    @NotNull
    @NotEmpty
    @NoDuplicates
    private List<Long> animalTypes;

}
