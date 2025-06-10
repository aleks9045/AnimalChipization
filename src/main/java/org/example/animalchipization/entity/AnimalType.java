package org.example.animalchipization.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents an animal type for animal entity
 *
 * <p>Has Many-to-Many relationships with {@link Animal}<br>
 * Stores string that determine animal type
 *
 * <p>Mapped with "animal_type" table in the database.
 *
 *
 * @author Aleksey
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"animals"})
@ToString(exclude = {"animals"})
@Table(name = "animal_type", indexes = {
        @Index(name = "idx_animal_type_id", columnList = "animal_type_id")
})
@Entity
public class AnimalType {

    @Id
    @SequenceGenerator(name = "animal_type_id_seq",
            sequenceName = "animal_type_id_seq",
            allocationSize = 10)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "animal_type_id_seq")
    @Column(name = "animal_type_id", nullable = false)
    private Long animalTypeId;

    @Column(name = "type", nullable = false, unique = true)
    private String type;

    @ManyToMany(mappedBy = "animalTypes")
    private Set<Animal> animals = new HashSet<>();
}
