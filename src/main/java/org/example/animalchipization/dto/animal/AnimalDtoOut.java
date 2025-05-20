package org.example.animalchipization.dto.animal;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.example.animalchipization.enums.AnimalGender;
import org.example.animalchipization.enums.AnimalLifeStatus;
import org.example.animalchipization.models.Account;
import org.example.animalchipization.models.Animal;
import org.example.animalchipization.models.AnimalType;
import org.example.animalchipization.models.Location;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents animal data transfer object
 *
 * <p>Used to display output data in responses<br>
 * All fields must be not null or blank excepts deathDateTime<br>
 *
 * @see Animal Animal entity
 * @author Aleksey
 */
@AllArgsConstructor
@Setter
@Getter
public class AnimalDtoOut {

    @NotNull
    @Positive
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
    private Set<Long> visitedLocations;

    @NotNull
    @NotBlank
    private AnimalLifeStatus lifeStatus;

    @Null
    private Instant deathDateTime;
}

