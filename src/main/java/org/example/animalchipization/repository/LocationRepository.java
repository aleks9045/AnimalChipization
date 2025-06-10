package org.example.animalchipization.repository;


import org.example.animalchipization.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Works with Location entity
 * <p>Extends JpaRepository, so it has a set of ready-made methods<br>
 * Also Extends JpaSpecificationExecutor for specifications execution
 *
 * @author Aleksey
 * @see Location Location entity
 */
@Repository
public interface LocationRepository extends
        JpaRepository<Location, Long>,
        JpaSpecificationExecutor<Location> {

    Boolean existsLocationByLatitudeAndLongitude(Double latitude, Double longitude);

    Boolean existsLocationByLocationId(Long locationId);

}