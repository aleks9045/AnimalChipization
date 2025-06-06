package org.example.animalchipization.dto.visitedLocation;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

/**
 * @author Aleksey
 */
@AllArgsConstructor
@Setter
@Getter
public class VisitedLocationDtoOut {

    @NotNull
    @Positive
    private Long id;

    @NotNull
    private Instant dateTimeOfVisitLocationPoint;

    @NotNull
    @Positive
    private Long locationPointId;
}
