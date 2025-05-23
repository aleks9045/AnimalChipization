package org.example.animalchipization.repository;


import org.example.animalchipization.entities.AnimalType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Works with Account entity
 * <p>Implements JpaRepository, so it has a set of ready-made methods
 *
 * @author Aleksey
 * @see AnimalType Animal type entity
 */
@Repository
public interface AnimalTypeRepository extends JpaRepository<AnimalType, Long> {

    Boolean existsAnimalTypeByType(String type);

}
