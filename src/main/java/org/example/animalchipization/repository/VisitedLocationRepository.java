package org.example.animalchipization.repository;

import org.example.animalchipization.entities.VisitedLocation;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/**
 * @author Aleksey
 */
public interface VisitedLocationRepository extends
        JpaRepository<VisitedLocation, Long>,
        JpaSpecificationExecutor<VisitedLocation> {

    @EntityGraph(
            type = EntityGraph.EntityGraphType.FETCH,
            attributePaths = {
                    "location"
            }
    )
    @Query("""
            SELECT v
            FROM VisitedLocation v
            WHERE v.visitedLocationPointId = :id
            """)
    VisitedLocation findJoinedWithLocationById(@Param("id") Long id);

    @EntityGraph(
            type = EntityGraph.EntityGraphType.FETCH,
            attributePaths = {
                    "animal",
                    "location"
            }
    )
    @Query("""
            SELECT v
            FROM VisitedLocation v
            WHERE v.animal.animalId = :animalId
            ORDER BY v.dateTimeOfVisitLocationPoint DESC
            LIMIT 1""")
    Optional<VisitedLocation> findLatterVisitedLocationByAnimalId(@Param("animalId") Long animalId);
}
