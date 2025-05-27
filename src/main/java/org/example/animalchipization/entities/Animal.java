package org.example.animalchipization.entities;

import jakarta.persistence.*;
import lombok.*;
import org.example.animalchipization.enums.AnimalGender;
import org.example.animalchipization.enums.AnimalLifeStatus;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents an animal entity.
 *
 * <p>Has Many-to-Many relationships with {@link AnimalType} and {@link Location} entities.<br>
 * Has One-to-Many relationship with {@link Account} entity.<br><br>
 *
 * Stores base information of animal (weight, length, height, gender)<br>
 * Includes animal's lifecycle mechanism -
 * death_date_time field sets automatically when life_status of animal is "DEAD"
 *
 * <p>Mapped with "animal" table in the database.
 *
 * @author Aleksey
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"chipperId", "chippingLocationId", "animalTypes", "visitedLocations"})
@ToString(exclude = {"chipperId", "chippingLocationId", "animalTypes", "visitedLocations"})
@Table(name = "animal", indexes = {
        @Index(name = "idx_animal_id", columnList = "animal_id")
})
@Entity
public class Animal {

    @Id
    @SequenceGenerator(name = "animal_id_seq",
            sequenceName = "animal_id_seq",
            allocationSize = 10)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "animal_id_seq")
    @Column(name = "animal_id", nullable = false)
    private Long animalId;

    @Column(name = "weight", nullable = false)
    private Float weight;

    @Column(name = "length", nullable = false)
    private Float length;

    @Column(name = "height", nullable = false)
    private Float height;

    @Column(name = "gender", nullable = false)
    private AnimalGender gender;

    @Column(name = "chipping_date_time", nullable = false)
    private Instant chippingDateTime = Instant.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chipper_id", nullable = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    private Account chipperId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chipping_location_id", nullable = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    private Location chippingLocationId;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "animal_types",
            joinColumns = @JoinColumn(name = "animal_id"),
            inverseJoinColumns = @JoinColumn(name = "animal_type_id")
    )
    private Set<AnimalType> animalTypes = new HashSet<>();

    @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY, mappedBy = "animal")
    private Set<VisitedLocation> visitedLocations = new HashSet<>();

    @Column(name = "life_status", nullable = false)
    private AnimalLifeStatus lifeStatus = AnimalLifeStatus.ALIVE;

    @Column(name = "death_date_time")
    private Instant deathDateTime;

    @PreUpdate
    private void updateDeathDateTime() {
        if (this.lifeStatus == AnimalLifeStatus.DEAD && this.deathDateTime == null) {
            this.deathDateTime = Instant.now();
        }
    }

}