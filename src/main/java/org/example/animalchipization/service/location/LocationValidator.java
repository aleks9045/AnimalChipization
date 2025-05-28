package org.example.animalchipization.service.location;

import org.example.animalchipization.entities.Location;
import org.example.animalchipization.enums.errors.LocationError;
import org.example.animalchipization.exception.entities.LocationException;
import org.example.animalchipization.repository.LocationRepository;
import org.springframework.stereotype.Component;

/**
 * @author Aleksey
 */
@Component
public class LocationValidator {

    private final LocationRepository locationRepository;

    public LocationValidator(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public Location validateAndGetLocation(Long locationId) {
        return locationRepository.findById(locationId)
                .orElseThrow(() -> new LocationException(LocationError.LOCATION_NOT_FOUND));
    }
    
    public void checkLocationExistence(Double latitude, Double longitude){
        if (locationRepository.existsLocationByLatitudeAndLongitude(
                latitude,
                longitude)) {
            throw new LocationException(LocationError.LOCATION_ALREADY_EXISTS);
        }
    }

    public void checkLocationExistence(Long locationId){
        if (!locationRepository.existsById(locationId)) {
            throw new LocationException(LocationError.LOCATION_NOT_FOUND);
        }
    }
}
