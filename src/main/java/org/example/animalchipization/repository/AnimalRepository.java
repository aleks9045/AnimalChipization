package org.example.animalchipization.repository;

import org.example.animalchipization.entities.Animal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Works with Account entity
 * <p>Implements JpaRepository, so it has a set of ready-made methods
 *
 * @author Aleksey
 * @see Animal Animal entity
 */
@Repository
public interface AnimalRepository extends JpaRepository<Animal, Long>, JpaSpecificationExecutor<Animal> {


}
