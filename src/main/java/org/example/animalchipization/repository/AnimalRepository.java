package org.example.animalchipization.repository;

import org.example.animalchipization.entity.Animal;
import org.example.animalchipization.entity.VisitedLocation;
import org.springframework.data.jpa.repository.*;
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
                    "animalTypes.animalTypeId"
            }
    )
    @Query("""
            SELECT a
            FROM Animal a
            WHERE a.animalId = :id
            """)
    Optional<Animal> findJoinedWithAllExceptVisitedLocationById(@Param("id") Long id);

    /**
     * This query exists to solve Cartesian product problem
     * @param animalId animal id
     * @return List of VisitedLocation
     */
    @Query("""
            SELECT vl FROM VisitedLocation vl
            INNER JOIN FETCH vl.location
            WHERE vl.animal.animalId = :animalId
            ORDER BY vl.dateTimeOfVisitLocationPoint
            """)
    List<VisitedLocation> findVisitedLocationsByAnimalId(@Param("animalId") Long animalId);

    @EntityGraph(
            type = EntityGraph.EntityGraphType.FETCH,
            attributePaths = {
                    "visitedLocations",
                    "visitedLocations.location"
            }
    )
    @Query("""
            SELECT a
            FROM Animal a
            WHERE a.animalId = :id
            """)
    Optional<Animal> findJoinedWithVisitedLocationById(@Param("id") Long id);


    @EntityGraph(
            type = EntityGraph.EntityGraphType.FETCH,
            attributePaths = {
                    "chippingLocationId",
                    "visitedLocations"
            }
    )
    @Query("""
            SELECT a
            FROM Animal a
            WHERE a.animalId = :id
            """)
    Optional<Animal> findJoinedWithLocationsById(@Param("id") Long id);

}
