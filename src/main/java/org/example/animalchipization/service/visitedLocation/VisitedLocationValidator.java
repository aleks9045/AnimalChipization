package org.example.animalchipization.service.visitedLocation;

import org.example.animalchipization.entities.Animal;
import org.example.animalchipization.entities.Location;
import org.example.animalchipization.entities.VisitedLocation;
import org.example.animalchipization.enums.errors.BadRequestError;
import org.example.animalchipization.enums.errors.NotFoundError;
import org.example.animalchipization.exception.RequestException;
import org.example.animalchipization.service.Validator;

import java.util.List;
import java.util.Objects;

/**
 * @author Aleksey
 */
public interface VisitedLocationValidator {

    Animal validateAndGetAnimalWithVisitedLocations(Long animalId);

    Animal validateAndGetAnimalWithAllLocations(Long animalId);

    void checkLatterFromAnimal(Animal animal, Location location);

    void checkVisitedLocationEqualsLocation(VisitedLocation VisitedLocation, Location location);

    VisitedLocation validateAndGetFromAnimal(
            Animal animal,
            Long visitedLocationId,
            Location location);

    VisitedLocation checkAndGetRemoval(Animal animal, Long visitedLocationId);

}
