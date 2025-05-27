package org.example.animalchipization.repository;

import org.example.animalchipization.entities.VisitedLocation;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Aleksey
 */
public interface VisitedLocationRepository extends JpaRepository<VisitedLocation, Long> {
}
