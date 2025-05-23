package org.example.animalchipization.dto.animal;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.example.animalchipization.enums.AnimalGender;
import org.example.animalchipization.enums.AnimalLifeStatus;

import java.util.Set;

/**
 * @author Aleksey
 */
@AllArgsConstructor
@Setter
@Getter
@ToString
public class AnimalDtoUpdate {

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

    @Schema(description = "animal life status", example = "DEAD")
    @NotNull
    private AnimalLifeStatus lifeStatus;

    @Schema(description = "animal chipper id", example = "1")
    @NotNull
    private Integer chipperId;

    @Schema(description = "animal chipping location id", example = "1")
    @NotNull
    private Long chippingLocationId;
}
