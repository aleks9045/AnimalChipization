package org.example.animalchipization.service.visitedLocation;

import org.example.animalchipization.entities.Animal;
import org.example.animalchipization.entities.Location;
import org.example.animalchipization.entities.VisitedLocation;

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

    void checkAndRemove(Animal animal, Long visitedLocationId);

}
