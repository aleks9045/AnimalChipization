package org.example.animalchipization.repository;

import io.micrometer.common.lang.NonNullApi;
import org.example.animalchipization.entities.Animal;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
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
public interface AnimalRepository extends JpaRepository<Animal, Long>, JpaSpecificationExecutor<Animal> {

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
    Optional<Animal> findById(Long animalId);
}
