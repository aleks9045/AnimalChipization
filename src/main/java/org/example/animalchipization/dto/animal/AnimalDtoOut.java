package org.example.animalchipization.dto.animal;


import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.example.animalchipization.enums.AnimalGender;
import org.example.animalchipization.enums.AnimalLifeStatus;

import org.example.animalchipization.entity.Animal;


import java.time.Instant;

import java.util.List;
import java.util.Set;

/**
 * Represents animal data transfer object
 *
 * <p>Used to display output data in responses<br><br>
 * All fields must be not null excepts deathDateTime<br>
 * Weight, length and height must be positive<br>
 * Each id field must be strictly greater than 0
 *
 * @see Animal Animal entity
 * @author Aleksey
 */
@AllArgsConstructor
@Setter
@Getter
@ToString
public class AnimalDtoOut {

    @NotNull
    @Positive
    @Min(1)
    private Long id;

    @NotNull
    @Positive
    private Float weight;

    @NotNull
    @Positive
    private Float length;

    @NotNull
    @Positive
    private Float height;

    @NotNull
    private AnimalGender gender;

    @NotNull
    private Instant chippingDateTime;

    @NotNull
    private Integer chipperId;

    @NotNull
    private Long chippingLocationId;

    @NotNull
    private Set<Long> animalTypes;

    @NotNull
    private List<Long> visitedLocations;

    @NotNull
    private AnimalLifeStatus lifeStatus;

    @Nullable
    private Instant deathDateTime;
}

