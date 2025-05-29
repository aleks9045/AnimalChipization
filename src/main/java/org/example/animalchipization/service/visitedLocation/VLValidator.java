package org.example.animalchipization.service.visitedLocation;

import org.example.animalchipization.entities.Animal;
import org.example.animalchipization.entities.VisitedLocation;
import org.example.animalchipization.enums.errors.AnimalError;
import org.example.animalchipization.enums.errors.VLError;
import org.example.animalchipization.exception.entities.AnimalException;
import org.example.animalchipization.exception.entities.VLException;
import org.example.animalchipization.repository.AnimalRepository;
import org.example.animalchipization.repository.LocationRepository;
import org.example.animalchipization.repository.VisitedLocationRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author Aleksey
 */
@Component
public class VLValidator {

    private final LocationRepository locationRepository;
    private final AnimalRepository animalRepository;
    private final VisitedLocationRepository visitedLocationRepository;

    public VLValidator(LocationRepository locationRepository, AnimalRepository animalRepository, VisitedLocationRepository visitedLocationRepository) {
        this.locationRepository = locationRepository;
        this.animalRepository = animalRepository;
        this.visitedLocationRepository = visitedLocationRepository;
    }

    public VisitedLocation validateAndGetVL(Long VLId) {
        return visitedLocationRepository.findById(VLId)
                .orElseThrow(() -> new VLException(VLError.VISITED_LOCATION_NOT_FOUND));
    }

    public Animal validateAndGetAnimal(Long animalId) {
        return animalRepository.findJoinedWithVisitedLocationById(animalId)
                .orElseThrow(() -> new AnimalException(AnimalError.ANIMAL_NOT_FOUND));
    }

    public void checkLatterVL(Long animalId, Long locationId) {
        Optional<VisitedLocation> latterVisitedLocation =
                visitedLocationRepository.findLatterVisitedLocationByAnimalId(animalId);

        if (latterVisitedLocation.isPresent() &&
                latterVisitedLocation.get().getLocation().getLocationId().equals(locationId)) {
            throw new AnimalException(AnimalError.ANIMAL_ALREADY_IN_LOCATION);
        }
    }
}
