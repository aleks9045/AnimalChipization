package org.example.animalchipization.dto.animal;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Aleksey
 */
@AllArgsConstructor
@Setter
@Getter
@ToString
public class UpdateAnimalTypeDto {

    @Schema(description = "old animal type id", example = "4")
    @NotNull
    @Positive
    @Min(1)
    private Long oldTypeId;

    @Schema(description = "new animal type id", example = "5")
    @NotNull
    @Positive
    @Min(1)
    private Long newTypeId;
}
