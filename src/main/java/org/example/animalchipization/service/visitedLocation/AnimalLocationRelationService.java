package org.example.animalchipization.service.visitedLocation;

import org.example.animalchipization.dto.location.VisitedLocationSearchCriteria;
import org.example.animalchipization.dto.visitedLocation.UpdateVisitedLocationDto;
import org.example.animalchipization.dto.visitedLocation.VisitedLocationDtoOut;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author Aleksey
 */
public interface AnimalLocationRelationService {

    List<VisitedLocationDtoOut> searchLocations(VisitedLocationSearchCriteria visitedLocationSearchCriteria,
                                                Pageable pageable);

    VisitedLocationDtoOut addVisitedLocationToAnimal(Long visitedLocationId, Long locationId);

    VisitedLocationDtoOut replaceVisitedLocationInAnimal(Long visitedLocationId, UpdateVisitedLocationDto updateVisitedLocationDto);

    void removeVisitedLocationFromAnimal(Long visitedLocationId, Long locationId);

}
