package org.example.animalchipization.repository;


import org.example.animalchipization.entities.AnimalType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    boolean existsAnimalTypeByType(String type);

    @Query("""
            SELECT CASE
                WHEN COUNT(a) > 0
                THEN true
                ELSE false
            END
            FROM Animal a
            JOIN a.animalTypes at ON at.animalTypeId = :animalTypeId""")
    boolean existsByAnimalTypeId(@Param("animalTypeId") Long animalTypeId);
}
