package org.example.animalchipization.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

}
