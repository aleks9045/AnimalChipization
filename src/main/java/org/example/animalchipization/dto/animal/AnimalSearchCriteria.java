package org.example.animalchipization.dto.animal;

import jakarta.annotation.Nullable;
import org.example.animalchipization.entity.Animal;
import org.example.animalchipization.enums.AnimalGender;
import org.example.animalchipization.enums.AnimalLifeStatus;

import java.time.Instant;

/**
 * Contains all fields that participates in search process for {@link Animal} entity
 * <p>All fields might be null
 *
 * @author Aleksey
 */
public record AnimalSearchCriteria (
        @Nullable Instant startDateTime,
        @Nullable Instant endDateTime,
        @Nullable Integer chipperId,
        @Nullable Long chippingLocationId,
        @Nullable AnimalLifeStatus lifeStatus,
        @Nullable AnimalGender gender) {}
