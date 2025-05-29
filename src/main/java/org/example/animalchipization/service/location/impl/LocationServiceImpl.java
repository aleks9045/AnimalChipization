package org.example.animalchipization.service.location.impl;

import org.example.animalchipization.dto.location.LocationDtoIn;
import org.example.animalchipization.dto.location.LocationDtoOut;
import org.example.animalchipization.entities.Location;
import org.example.animalchipization.enums.errors.LocationError;
import org.example.animalchipization.exception.entities.LocationException;
import org.example.animalchipization.mappers.LocationMapper;
import org.example.animalchipization.repository.LocationRepository;
import org.example.animalchipization.service.location.LocationService;
import org.example.animalchipization.service.location.LocationValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author Aleksey
 */
@Service
public class LocationServiceImpl implements LocationService {
    private final LocationRepository locationRepository;
    private final LocationMapper locationMapper;
    private final LocationValidator locationValidator;

    @Autowired
    public LocationServiceImpl(LocationRepository locationRepository, LocationMapper locationMapper, LocationValidator locationValidator) {
        this.locationRepository = locationRepository;
        this.locationMapper = locationMapper;
        this.locationValidator = locationValidator;
    }

    @Override
    public LocationDtoOut getLocation(Long locationId) {

        Location location = locationValidator.validateAndGetLocation(locationId);

        return locationMapper.toDto(location);
    }

    @Override
    @Transactional
    public LocationDtoOut addLocation(LocationDtoIn locationDtoIn) {

        locationValidator.checkLocationExistence(
                locationDtoIn.getLatitude(),
                locationDtoIn.getLongitude()
        );

        Location location = locationMapper.toEntity(locationDtoIn);
        locationRepository.save(location);

        return locationMapper.toDto(location);
    }

    @Override
    @Transactional
    public LocationDtoOut updateLocation(Long locationId, LocationDtoIn locationDtoIn) {

        locationValidator.checkLocationExistence(locationId);

        Location location = locationMapper.toEntity(locationDtoIn);
        location.setLocationId(locationId);
        locationRepository.save(location);

        return locationMapper.toDto(location);
    }

    @Override
    public void deleteLocationById(Long locationId) {

        locationValidator.checkLocationExistence(locationId);
        try {
            locationRepository.deleteById(locationId);
        } catch (Exception e) {
            throw new LocationException(LocationError.LOCATION_STILL_LINKED);
        }
    }
}
