package org.example.animalchipization.dto.location;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;

/**
 * @author Aleksey
 */
public record LocationSearchCriteria(
        Long animalId,
        @Nullable Instant startDateTime,
        @Nullable Instant endDateTime) {}
