package org.example.animalchipization.dto.animal;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.example.animalchipization.enums.AnimalGender;
import org.example.animalchipization.enums.AnimalLifeStatus;
import org.example.animalchipization.models.Account;
import org.example.animalchipization.models.Animal;
import org.example.animalchipization.models.AnimalType;
import org.example.animalchipization.models.Location;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.Instant;
import java.util.HashSet;
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
public class AnimalDtoIn {

    @Schema(description = "animal weight", example = "4.4")
    @NotNull @NotBlank
    @Positive
    private Float weight;

    @Schema(description = "animal length", example = "0.8")
    @NotNull @NotBlank
    @Positive
    private Float length;

    @Schema(description = "animal height", example = "0.4")
    @NotNull @NotBlank
    @Positive
    private Float height;

    @Schema(description = "animal gender", example = "MALE")
    @NotNull @NotBlank
    private AnimalGender gender;

    @Schema(description = "animal chipper id", example = "1")
    @NotNull @NotBlank
    private Integer chipperId;

    @Schema(description = "animal chipping location id", example = "1")
    @NotNull @NotBlank
    private Long chippingLocationId;

    @Schema(description = "animal animal types", example = "1, 2")
    @NotNull @NotBlank
    private Set<Long> animalTypes;

}
