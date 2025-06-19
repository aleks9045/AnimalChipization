package org.example.animalchipization.dto.animal;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.example.animalchipization.enums.AnimalGender;

import org.example.animalchipization.entity.Animal;
import org.example.animalchipization.annotation.annotation.NoDuplicates;

import java.util.List;

/**
 * Represents animal data transfer object
 *
 * @author Aleksey
 * @see Animal Animal entity
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
    @Positive
    @Min(1)
    private Integer chipperId;

    @Schema(description = "animal chipping location id", example = "1")
    @NotNull
    @Positive
    @Min(1)
    private Long chippingLocationId;

    @Schema(description = "animal types", example = "[1]")
    @NotEmpty
    @NoDuplicates
    private List<@Positive @Min(1) Long> animalTypes;

}
