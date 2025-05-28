package org.example.animalchipization.service.visitedLocation;

import org.example.animalchipization.entities.Location;
import org.example.animalchipization.enums.errors.LocationError;
import org.example.animalchipization.exception.entities.LocationException;
import org.example.animalchipization.repository.AnimalRepository;
import org.example.animalchipization.repository.LocationRepository;
import org.example.animalchipization.repository.VisitedLocationRepository;
import org.springframework.stereotype.Component;

/**
 * @author Aleksey
 */
@Component
public class VisitedLocationValidator {

    private final LocationRepository locationRepository;
    private final AnimalRepository animalRepository;
    private final VisitedLocationRepository visitedLocationRepository;

    public VisitedLocationValidator(LocationRepository locationRepository, AnimalRepository animalRepository, VisitedLocationRepository visitedLocationRepository) {
        this.locationRepository = locationRepository;
        this.animalRepository = animalRepository;
        this.visitedLocationRepository = visitedLocationRepository;
    }


}
