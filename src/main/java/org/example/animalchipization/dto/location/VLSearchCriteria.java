package org.example.animalchipization.dto.location;

import jakarta.annotation.Nullable;

import java.time.Instant;

/**
 * @author Aleksey
 */
public record VLSearchCriteria(
        Long animalId,
        @Nullable Instant startDateTime,
        @Nullable Instant endDateTime) {}
