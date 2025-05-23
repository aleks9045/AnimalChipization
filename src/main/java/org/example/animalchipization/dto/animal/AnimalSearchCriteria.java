package org.example.animalchipization.dto.animal;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.example.animalchipization.enums.AnimalGender;
import org.example.animalchipization.enums.AnimalLifeStatus;

import java.time.Instant;

/**
 * @author Aleksey
 */
public record AnimalSearchCriteria (
        @Nullable Instant startDateTime,
        @Nullable Instant endDateTime,
        @Nullable Integer chipperId,
        @Nullable Long chippingLocationId,
        @Nullable AnimalLifeStatus lifeStatus,
        @Nullable AnimalGender gender) {}
