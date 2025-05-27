package org.example.animalchipization.repository;

import org.example.animalchipization.entities.Animal;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Works with Account entity
 * <p>Implements JpaRepository, so it has a set of ready-made methods
 *
 * @author Aleksey
 * @see Animal Animal entity
 */
@Repository
public interface AnimalRepository extends
        JpaRepository<Animal, Long>,
        JpaSpecificationExecutor<Animal> {

    @EntityGraph(
            type = EntityGraph.EntityGraphType.FETCH,
            attributePaths = {
                    "chipperId.accountId",
                    "chippingLocationId.locationId",
                    "animalTypes.animalTypeId",
                    "visitedLocations",
                    "visitedLocations.location"
            }
    )
    @Query("""
            SELECT a from Animal a WHERE a.animalId = :animalId
            """)
    Optional<Animal> findJoinedWithAllById(Long animalId);

    @EntityGraph(
            type = EntityGraph.EntityGraphType.FETCH,
            attributePaths = {
                    "visitedLocations",
                    "visitedLocations.location"
            }
    )
    @Query("""
            SELECT a from Animal a WHERE a.animalId = :animalId
            """)
    Optional<Animal> findJoinedWithVisitedLocationById(Long animalId);


}
