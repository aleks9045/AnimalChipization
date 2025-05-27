package org.example.animalchipization.repository;

import org.example.animalchipization.entities.VisitedLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author Aleksey
 */
public interface VisitedLocationRepository extends
        JpaRepository<VisitedLocation, Long>,
        JpaSpecificationExecutor<VisitedLocation> {

}
