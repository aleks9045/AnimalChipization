package org.example.animalchipization.dto.visitedLocation;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Aleksey
 */
@AllArgsConstructor
@Setter
@Getter
public class UpdateVLDto {

    @NotNull
    @Positive
    @Min(1)
    private Long visitedLocationPointId;

    @NotNull
    @Positive
    @Min(1)
    private Long locationPointId;

}
