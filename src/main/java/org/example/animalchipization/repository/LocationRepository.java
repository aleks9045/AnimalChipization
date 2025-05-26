package org.example.animalchipization.repository;


import org.example.animalchipization.entities.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Works with Location entity
 * <p>Implements JpaRepository, so it has a set of ready-made methods
 *
 * @author Aleksey
 * @see Location Location entity
 */
@Repository
public interface LocationRepository extends JpaRepository<Location, Long>, JpaSpecificationExecutor<Location> {

    Boolean existsLocationByLatitudeAndLongitude(Double latitude, Double longitude);

    Boolean existsLocationByLocationId(Long locationId);

}