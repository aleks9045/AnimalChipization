package org.example.animalchipization.service.location.impl;

import org.example.animalchipization.entities.Location;
import org.example.animalchipization.enums.errors.ConflictError;
import org.example.animalchipization.enums.errors.NotFoundError;
import org.example.animalchipization.exception.RequestException;
import org.example.animalchipization.repository.LocationRepository;
import org.example.animalchipization.service.Validator;
import org.example.animalchipization.service.location.LocationValidator;
import org.springframework.stereotype.Component;

/**
 * @author Aleksey
 */
@Component
public class LocationValidatorImpl implements LocationValidator {

    private final LocationRepository locationRepository;

    public LocationValidatorImpl(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    @Override
    public Location validateAndGetById(Long locationId) {
        return locationRepository.findById(locationId)
                .orElseThrow(() -> new RequestException(NotFoundError.LOCATION_NOT_FOUND));
    }

    @Override
    public void checkExistence(Double latitude, Double longitude) {
        if (locationRepository.existsLocationByLatitudeAndLongitude(
                latitude,
                longitude)) {
            throw new RequestException(ConflictError.LOCATION_ALREADY_EXISTS);
        }
    }

    @Override
    public void checkExistence(Long locationId) {
        if (!locationRepository.existsById(locationId)) {
            throw new RequestException(NotFoundError.LOCATION_NOT_FOUND);
        }
    }
}
