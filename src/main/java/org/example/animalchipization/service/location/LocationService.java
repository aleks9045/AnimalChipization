package org.example.animalchipization.service.location;

import org.example.animalchipization.dto.location.LocationDtoIn;
import org.example.animalchipization.dto.location.LocationDtoOut;
import org.example.animalchipization.entity.Location;
import org.example.animalchipization.service.location.impl.LocationServiceImpl;

/**
 *  A set of methods for business logic with {@link Location} entity
 *
 *  @author Aleksey
 *
 *  @see LocationServiceImpl Implementation of this interface
 */

public interface LocationService {
    /**
     * Gets location entry from database
     *
     * @param locationId location id
     * @return {@link LocationDtoOut}
     */
    LocationDtoOut getLocation(Long locationId);


    /**
     * Adds location entry from database
     *
     * @param locationDtoIn location schema {@link LocationDtoIn}
     */
    LocationDtoOut addLocation(LocationDtoIn locationDtoIn);

    /**
     * Updates location entry in database
     *
     * @param locationId location id
     * @param locationDtoIn {@link LocationDtoIn}
     * @return {@link LocationDtoOut}
     */
    LocationDtoOut updateLocation(Long locationId, LocationDtoIn locationDtoIn);

    /**
     * Deletes location entry from database
     *
     * @param locationId location id
     */
    void deleteLocationById(Long locationId);

}

