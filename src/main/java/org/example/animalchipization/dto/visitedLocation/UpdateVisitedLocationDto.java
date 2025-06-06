package org.example.animalchipization.dto.visitedLocation;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents visited location data transfer object
 *
 * <p>Used to update visited location entity<br><br>
 * All fields must be not null<br>
 * Each id field must be strictly greater than 0
 *
 * @author Aleksey
 */
@AllArgsConstructor
@Setter
@Getter
public class UpdateVisitedLocationDto {

    @NotNull
    @Positive
    @Min(1)
    private Long visitedLocationPointId;

    @NotNull
    @Positive
    @Min(1)
    private Long locationPointId;

}
