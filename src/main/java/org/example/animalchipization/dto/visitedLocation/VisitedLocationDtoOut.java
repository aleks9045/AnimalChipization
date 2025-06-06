package org.example.animalchipization.dto.visitedLocation;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

/**
 * Represents visited location data transfer object
 * All fields must be not null<br>
 * Each id field must be strictly greater than 0
 *
 * @author Aleksey
 */
@AllArgsConstructor
@Setter
@Getter
public class VisitedLocationDtoOut {

    @NotNull
    @Positive
    @Min(1)
    private Long id;

    @NotNull
    private Instant dateTimeOfVisitLocationPoint;

    @NotNull
    @Positive
    @Min(1)
    private Long locationPointId;
}
