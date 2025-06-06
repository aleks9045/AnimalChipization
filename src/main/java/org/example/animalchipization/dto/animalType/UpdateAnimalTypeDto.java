package org.example.animalchipization.dto.animalType;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Represents animal type data transfer object
 *
 * <p>Used to update animal entity<br><br>
 * All fields must be not null<br>
 * Each id field must be strictly greater than 0
 *
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
