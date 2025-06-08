package org.example.animalchipization.service.location.impl;

import lombok.RequiredArgsConstructor;
import org.example.animalchipization.dto.location.LocationDtoIn;
import org.example.animalchipization.dto.location.LocationDtoOut;
import org.example.animalchipization.entities.Location;
import org.example.animalchipization.enums.errors.BadRequestError;
import org.example.animalchipization.exception.RequestException;
import org.example.animalchipization.mappers.LocationMapper;
import org.example.animalchipization.repository.LocationRepository;
import org.example.animalchipization.service.location.LocationService;
import org.example.animalchipization.service.location.LocationValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author Aleksey
 */
@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;
    private final LocationMapper locationMapper;
    private final LocationValidator locationValidator;


    @Override
    public LocationDtoOut getLocation(Long locationId) {

        Location location = locationValidator.validateAndGetById(locationId);

        return locationMapper.toDto(location);
    }

    @Override
    public LocationDtoOut addLocation(LocationDtoIn locationDtoIn) {

        locationValidator.checkExistence(
                locationDtoIn.getLatitude(),
                locationDtoIn.getLongitude()
        );

        Location location = locationMapper.toEntity(locationDtoIn);
        Location savedLocation = locationRepository.save(location);

        return locationMapper.toDto(savedLocation);
    }

    @Override
    @Transactional
    public LocationDtoOut updateLocation(Long locationId, LocationDtoIn locationDtoIn) {

        locationValidator.checkExistence(locationId);

        Location location = locationMapper.toEntity(locationDtoIn);
        location.setLocationId(locationId);
        locationRepository.save(location);

        return locationMapper.toDto(location);
    }

    @Override
    public void deleteLocationById(Long locationId) {

        locationValidator.checkExistence(locationId);
        try {
            locationRepository.deleteById(locationId);
        } catch (Exception e) {
            throw new RequestException(BadRequestError.LOCATION_STILL_LINKED);
        }
    }
}
