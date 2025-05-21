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
@ToString
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

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinTable(name = "animal_animalType",
            joinColumns = @JoinColumn(name = "animal_id"),
            inverseJoinColumns = @JoinColumn(name = "animal_type_id")
    )
    private Set<AnimalType> animalTypes = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinTable(name = "animal_location",
            joinColumns = @JoinColumn(name = "animal_id"),
            inverseJoinColumns = @JoinColumn(name = "location_id")
    )
    private Set<Location> visitedLocations = new HashSet<>();

    @Column(name = "life_status", nullable = false)
    private AnimalLifeStatus lifeStatus = AnimalLifeStatus.ALIVE;

    @Column(name = "death_date_time")
    private Instant deathDateTime;

    @PrePersist
    @PreUpdate
    private void updateDeathDateTime() {
        if (this.lifeStatus == AnimalLifeStatus.DEAD && this.deathDateTime == null) {
            this.deathDateTime = Instant.now();
        }
    }

}


//"id":"long",                // Идентификатор животного
//        "animalTypes":"[long]",        // Массив идентификаторов типов животного
//        "weight":"float",            // Масса животного, кг
//        "length":"float",            // Длина животного, м
//        "height":"float",            // Высота животного, м
//        "gender":"string",            // Гендерный признак животного, доступные значения “MALE”, “FEMALE”, “OTHER”
//        "lifeStatus":"string",            // Жизненный статус животного, доступные значения “ALIVE”(устанавливается автоматически при добавлении нового животного), “DEAD”(можно установить при обновлении информации о животном)
//        "chippingDateTime":"dateTime",    // Дата и время чипирования в формате ISO-8601 (устанавливается автоматически на момент добавления животного)
//        "chipperId":"int",            // Идентификатор аккаунта пользователя, чипировавшего животное
//        "chippingLocationId":"long",        // Идентификатор точки локации животных
//        "visitedLocations":"[long]",        // Массив идентификаторов объектов с информацией о посещенных точках локаций
//        "deathDateTime":"dateTime"