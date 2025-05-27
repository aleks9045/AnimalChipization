package org.example.animalchipization.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents a location for animal entity
 *
 * <p>Mapped with "location" table in the database.
 *
 * @author Aleksey
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"visitedLocations"})
@ToString(exclude = {"visitedLocations"})
@Table(name = "location",
        indexes = {
        @Index(name = "idx_location_id", columnList = "location_id")
        },
        uniqueConstraints = {
        @UniqueConstraint(name = "location_cords_unique", columnNames = {"latitude", "longitude"})
        }
    )
@Entity
public class Location {
    @Id
    @SequenceGenerator(name = "location_id_seq", sequenceName = "location_seq_id", allocationSize = 10)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "location_seq_id")
    @Column(name = "location_id", nullable = false)
    private Long locationId;

    @Column(name = "latitude", nullable = false)
    private Double latitude;

    @Column(name = "longitude", nullable = false)
    private Double longitude;

    @OneToMany(mappedBy = "location")
    private Set<VisitedLocation> visitedLocations = new HashSet<>();
}
