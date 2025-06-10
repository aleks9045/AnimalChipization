package org.example.animalchipization.repository;

import org.example.animalchipization.entities.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Works with Account entity
 * <p>Extends JpaRepository, so it has a set of ready-made methods<br>
 * Also Extends JpaSpecificationExecutor for specifications execution
 *
 * @author Aleksey
 * @see Account Location entity
 */
@Repository
public interface AccountRepository extends
        JpaRepository<Account, Long>,
        JpaSpecificationExecutor<Account> {

    Optional<Account> findByEmail(String email);

    Boolean existsAccountByAccountId(Integer accountId);

    @Query(value = """
            SELECT *
            FROM account a
            WHERE ((lower(a.first_name) LIKE '%' || lower(:firstName) || '%') OR (:firstName is null)) AND
            ((lower(a.last_name) LIKE '%' || lower(:lastName) || '%') OR (:lastName is null)) AND
            ((lower(a.email) LIKE '%' || lower(:email) || '%') OR (:email is null))
            ORDER BY a.account_id ASC
            LIMIT :limit
            OFFSET :offset
            """, nativeQuery = true)
    List<Account> searchAccountsByFirstNameAndLastNameAndEmail(
            @Param("firstName") String firstName,
            @Param("lastName") String lastName,
            @Param("email") String email,
            @Param("limit") int limit,
            @Param("offset") int offset);

}
