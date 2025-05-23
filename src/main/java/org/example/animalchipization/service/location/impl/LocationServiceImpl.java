package org.example.animalchipization.service.location.impl;

import org.example.animalchipization.dto.location.LocationDtoIn;
import org.example.animalchipization.dto.location.LocationDtoOut;
import org.example.animalchipization.entities.Location;
import org.example.animalchipization.enums.errors.LocationError;
import org.example.animalchipization.exception.entities.LocationException;
import org.example.animalchipization.mappers.LocationMapper;
import org.example.animalchipization.repository.LocationRepository;
import org.example.animalchipization.service.location.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

/**
 * @author Aleksey
 */
@Service
public class LocationServiceImpl implements LocationService {
    private final LocationRepository locationRepository;
    private final LocationMapper locationMapper;

    @Autowired
    public LocationServiceImpl(LocationRepository locationRepository, LocationMapper locationMapper) {
        this.locationRepository = locationRepository;
        this.locationMapper = locationMapper;
    }

    @Override
    public LocationDtoOut getLocation(Long locationId) {

        Location location = locationRepository.findById(locationId)
                .orElseThrow(() -> new LocationException(LocationError.LOCATION_NOT_FOUND));

        return locationMapper.toDto(location);
    }

    @Override
    public LocationDtoOut addLocation(LocationDtoIn locationDtoIn) {
        if (locationRepository.existsLocationByLatitudeAndLongitude(
                locationDtoIn.getLatitude(),
                locationDtoIn.getLongitude())
        ) {
            throw new LocationException(LocationError.LOCATION_ALREADY_EXISTS);
        }

        Location location = locationMapper.toEntity(locationDtoIn);
        Location savedLocation = locationRepository.save(location);

        return locationMapper.toDto(savedLocation);
    }

    @Override
    public LocationDtoOut updateLocation(Long locationId, LocationDtoIn locationDtoIn) {
        if (locationRepository.existsLocationByLatitudeAndLongitude(
                locationDtoIn.getLatitude(),
                locationDtoIn.getLongitude())
        ) {
            throw new LocationException(LocationError.LOCATION_ALREADY_EXISTS);
        }
        Location location = locationMapper.toEntity(locationDtoIn);
        location.setLocationId(locationId);
        Location savedLocation = locationRepository.save(location);

        return locationMapper.toDto(savedLocation);
    }

    @Override
    public void deleteLocationById(Long locationId) {
        locationRepository.deleteById(locationId);
    }
}
