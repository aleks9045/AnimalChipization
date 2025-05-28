package org.example.animalchipization.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

/**
 * @author Aleksey
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"animal", "location"})
@ToString(exclude = {"animal", "location"})
@Table(name = "visited_location", indexes = {
        @Index(name = "idx_visit_time", columnList = "dateTimeOfVisitLocationPoint")
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
    private Animal animal;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    @Column(name = "visit_time", nullable = false)
    private final Instant dateTimeOfVisitLocationPoint = Instant.now();
}
