package org.example.animalchipization.repository;

import org.example.animalchipization.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
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

}
