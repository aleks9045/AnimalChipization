package org.example.animalchipization.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

/**
 * Represents visited location entity
 *
 * <p>Is an intermediary table for {@link Animal} and {@link Location} entities<br>
 * Stores animal and location id and definite where and when was animal
 *
 * <p>Mapped with "visited_location" table in the database.
 * @author Aleksey
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"animal", "location"})
@ToString(exclude = {"animal", "location"})
@Table(name = "visited_location", indexes = {
        @Index(name = "idx_visit_time", columnList = "visit_time")
})
@Entity
public class VisitedLocation {

    @Id
    @SequenceGenerator(name = "visited_location_id_seq",
            sequenceName = "visited_location_id_seq",
            allocationSize = 10)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "visited_location_id_seq")
    @Column(name = "visited_location_point_id", nullable = false)
    private Long visitedLocationPointId;

    @ManyToOne
    @JoinColumn(name = "animal_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Animal animal;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    @Column(name = "visit_time", nullable = false)
    private final Instant dateTimeOfVisitLocationPoint = Instant.now().truncatedTo(ChronoUnit.MICROS);
}
