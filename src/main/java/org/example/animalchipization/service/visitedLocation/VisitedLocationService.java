package org.example.animalchipization.service.visitedLocation;

import org.example.animalchipization.dto.location.VLSearchCriteria;
import org.example.animalchipization.dto.visitedLocation.UpdateVLDto;
import org.example.animalchipization.dto.visitedLocation.VLDtoOut;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author Aleksey
 */
public interface VisitedLocationService {

    List<VLDtoOut> searchLocations(VLSearchCriteria VLSearchCriteria,
                                   Pageable pageable);

    VLDtoOut addVisitedLocationToAnimal(Long visitedLocationId, Long locationId);

    VLDtoOut replaceVisitedLocationInAnimal(Long visitedLocationId, UpdateVLDto updateVLDto);

    void removeVisitedLocationFromAnimal(Long visitedLocationId, Long locationId);

}
