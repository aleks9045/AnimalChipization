package org.example.animalchipization.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents a user account entity.<br>
 * Contains personal identification information (first name, last name, email)
 * and authentication credentials (password).
 *
 * <p>Mapped to the "account" table in the database.
 *
 * @author Aleksey
 */

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"animals"})
@ToString(exclude = {"animals"})
@Entity
@Table(name = "account", indexes = {
        @Index(name = "idx_account_id", columnList = "account_id")
})
public class Account {

    @Id
    @SequenceGenerator(name = "account_id_seq",
            sequenceName = "account_id_seq",
            allocationSize = 10)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "account_id_seq")
    @Column(name = "account_id", nullable = false)
    private Integer accountId;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "base64", nullable = false)
    private String base64;

    @OneToMany(mappedBy = "chipperId")
    private Set<Animal> animals = new HashSet<>();

}
