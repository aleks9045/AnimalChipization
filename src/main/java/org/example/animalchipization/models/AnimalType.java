package org.example.animalchipization.models;

import jakarta.persistence.*;
import lombok.*;

/**
 * @author Aleksey
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
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
}
