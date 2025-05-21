package org.example.animalchipization.entities;

import jakarta.persistence.*;
import lombok.*;

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
@EqualsAndHashCode
@ToString
@Table(name = "location", indexes = {
        @Index(name = "idx_location_id", columnList = "location_id")
})
@Entity
public class Location {
    @Id
    @SequenceGenerator(name = "location_id_seq",
            sequenceName = "location_seq_id",
            allocationSize = 10)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "location_seq_id")
    @Column(name = "location_id", nullable = false)
    private Long locationId;

    @Column(name = "latitude",nullable = false, unique = true)
    private Double latitude;

    @Column(name = "longitude",nullable = false, unique = true)
    private Double longitude;
}
