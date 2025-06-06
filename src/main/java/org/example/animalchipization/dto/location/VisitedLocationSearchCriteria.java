package org.example.animalchipization.dto.location;

import jakarta.annotation.Nullable;
import org.example.animalchipization.entities.Animal;
import org.example.animalchipization.entities.VisitedLocation;

import java.time.Instant;

/**
 * Contains all fields that participates in search process for {@link VisitedLocation} entity
 * <p>All fields might be null
 *
 * @author Aleksey
 */
public record VisitedLocationSearchCriteria(
        Long animalId,
        @Nullable Instant startDateTime,
        @Nullable Instant endDateTime) {}
