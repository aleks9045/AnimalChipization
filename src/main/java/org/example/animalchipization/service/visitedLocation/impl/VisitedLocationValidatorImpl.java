package org.example.animalchipization.service.visitedLocation.impl;

import lombok.RequiredArgsConstructor;
import org.example.animalchipization.entities.Animal;
import org.example.animalchipization.entities.Location;
import org.example.animalchipization.entities.VisitedLocation;
import org.example.animalchipization.enums.errors.BadRequestError;
import org.example.animalchipization.enums.errors.NotFoundError;
import org.example.animalchipization.exception.RequestException;
import org.example.animalchipization.repository.AnimalRepository;
import org.example.animalchipization.repository.VisitedLocationRepository;
import org.example.animalchipization.service.visitedLocation.VisitedLocationValidator;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

/**
 * @author Aleksey
 */
@Component
@RequiredArgsConstructor
public class VisitedLocationValidatorImpl implements VisitedLocationValidator {

    private final AnimalRepository animalRepository;
    private final VisitedLocationRepository visitedLocationRepository;

    public Animal validateAndGetAnimalWithVisitedLocations(Long animalId) {
        return animalRepository.findJoinedWithVisitedLocationById(animalId)
                .orElseThrow(() -> new RequestException(NotFoundError.ANIMAL_NOT_FOUND));
    }

    public Animal validateAndGetAnimalWithAllLocations(Long animalId) {
        return animalRepository.findJoinedWithLocationsById(animalId)
                .orElseThrow(() -> new RequestException(NotFoundError.ANIMAL_NOT_FOUND));
    }

    public void checkLatterFromAnimal(Animal animal, Location location) {

        List<VisitedLocation> visitedLocations = animal.getVisitedLocations();

        if (!visitedLocations.isEmpty()) {
            VisitedLocation latterVisitedLocation =
                    visitedLocations.get(visitedLocations.size() - 1);

            if (latterVisitedLocation.getLocation().equals(location)) {
                throw new RequestException(BadRequestError.ANIMAL_ALREADY_IN_LOCATION);
            }
        }
    }

    public void checkVisitedLocationEqualsLocation(VisitedLocation VisitedLocation, Location location) {
        if (Objects.equals(VisitedLocation.getLocation(), location)) {
            throw new RequestException(BadRequestError.VISITED_LOCATION_EQUALS_LOCATION);
        }
    }

    public VisitedLocation validateAndGetFromAnimal(
            Animal animal,
            Long visitedLocationId,
            Location location) {

        List<VisitedLocation> visitedLocations = animal.getVisitedLocations();

        int listSize = visitedLocations.size();

        for (int i = 0; i < listSize; i++) {

            if (visitedLocations.get(i).getVisitedLocationPointId().equals(visitedLocationId)) {

                if (i > 0) this.checkPrevious(visitedLocations.get(i - 1), location);

                if (i < listSize - 1) this.checkNext(visitedLocations.get(i + 1), location);

                if (i == 0) this.checkLocationEqualLocation(
                        location,
                        animal.getChippingLocationId()
                );

                return visitedLocations.get(i);
            }
        }
        throw new RequestException(NotFoundError.VISITED_LOCATION_NOT_FOUND);
    }

    private void checkPrevious(VisitedLocation visitedLocation,
                               Location location) {
        if (visitedLocation.getLocation().equals(location)) {
            throw new RequestException(BadRequestError.VISITED_LOCATION_EQUALS_PREVIOUS);
        }
    }

    private void checkNext(VisitedLocation visitedLocation,
                           Location location) {
        if (visitedLocation.getLocation().equals(location)) {
            throw new RequestException(BadRequestError.VISITED_LOCATION_EQUALS_NEXT);
        }
    }

    public void checkAndRemove(Animal animal, Long visitedLocationId) {

        List<VisitedLocation> visitedLocations = animal.getVisitedLocations();

        int listSize = visitedLocations.size();

        for (int i = 0; i < listSize; i++) {
            var currentElement = visitedLocations.get(i);

            if (currentElement.getVisitedLocationPointId().equals(visitedLocationId)) {

                if (i > 0 && i < listSize - 1)
                    this.checkLocationEqualLocation(
                            visitedLocations.get(i - 1).getLocation(),
                            visitedLocations.get(i + 1).getLocation()
                    );

                if (i == 0 && listSize > 1 &&
                        animal.getChippingLocationId()
                                .equals(visitedLocations.get(i + 1).getLocation())) {

                    // Remove visited location link from animal and location entities,
                    // also remove it directly from database
                    var nextElement = visitedLocations.get(i + 1);
                    var location = animal.getVisitedLocations().get(i + 1).getLocation();

                    animal.getVisitedLocations().remove(nextElement);
                    location.getVisitedLocations().remove(nextElement);

                    visitedLocationRepository.delete(nextElement);
                }
                var location = animal.getVisitedLocations().get(i).getLocation();

                animal.getVisitedLocations().remove(currentElement);
                location.getVisitedLocations().remove(currentElement);

                visitedLocationRepository.delete(currentElement);
                return;
            }
        }
        throw new RequestException(NotFoundError.VISITED_LOCATION_NOT_FOUND);
    }

    private void checkLocationEqualLocation(Location location1,
                                            Location location2) {
        if (location1.equals(location2)) {
            throw new RequestException(BadRequestError.PREVIOUS_LOCATION_EQUALS_NEXT);
        }
    }

}
