package org.example.animalchipization.service.visitedLocation.impl;

import jakarta.persistence.criteria.JoinType;
import org.example.animalchipization.dto.location.VisitedLocationSearchCriteria;
import org.example.animalchipization.dto.visitedLocation.UpdateVisitedLocationDto;
import org.example.animalchipization.dto.visitedLocation.VisitedLocationDtoOut;
import org.example.animalchipization.entities.Animal;
import org.example.animalchipization.entities.Location;
import org.example.animalchipization.entities.VisitedLocation;
import org.example.animalchipization.enums.AnimalLifeStatus;
import org.example.animalchipization.enums.errors.AnimalError;
import org.example.animalchipization.enums.errors.LocationError;
import org.example.animalchipization.enums.errors.VisitedLocationError;
import org.example.animalchipization.exception.entities.AnimalException;
import org.example.animalchipization.exception.entities.LocationException;
import org.example.animalchipization.exception.entities.VisitedLocationException;
import org.example.animalchipization.mappers.VisitedLocationMapper;
import org.example.animalchipization.repository.AnimalRepository;
import org.example.animalchipization.repository.LocationRepository;
import org.example.animalchipization.repository.VisitedLocationRepository;
import org.example.animalchipization.service.visitedLocation.VisitedLocationService;
import org.example.animalchipization.service.JpaSpecificationBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @author Aleksey
 */
@Service
public class VisitedLocationServiceImpl implements VisitedLocationService {

    private final VisitedLocationRepository visitedLocationRepository;
    private final VisitedLocationMapper visitedLocationMapper;
    private final AnimalRepository animalRepository;
    private final LocationRepository locationRepository;

    @Autowired
    public VisitedLocationServiceImpl(VisitedLocationRepository visitedLocationRepository, VisitedLocationMapper visitedLocationMapper, AnimalRepository animalRepository, LocationRepository locationRepository) {
        this.visitedLocationRepository = visitedLocationRepository;
        this.visitedLocationMapper = visitedLocationMapper;
        this.animalRepository = animalRepository;
        this.locationRepository = locationRepository;
    }

    @Override
    @Transactional
    public List<VisitedLocationDtoOut> searchLocations(VisitedLocationSearchCriteria visitedLocationSearchCriteria, Pageable pageable) {

        Specification<VisitedLocation> spec = Specification.where(
                JpaSpecificationBuilder.<VisitedLocation>join(
                        "animal",
                        "animalId",
                        visitedLocationSearchCriteria.animalId(),
                        JoinType.INNER)
        ).and(
                JpaSpecificationBuilder.dateTimeFrom(
                        "dateTimeOfVisitLocationPoint",
                        visitedLocationSearchCriteria.startDateTime())
        ).and(
                JpaSpecificationBuilder.dateTimeTo(
                        "dateTimeOfVisitLocationPoint",
                        visitedLocationSearchCriteria.endDateTime())
        );

        Page<VisitedLocation> locationPage = visitedLocationRepository.findAll(spec, pageable);

        return locationPage.map(visitedLocationMapper::toDto).getContent();
    }

    @Override
    @Transactional
    public VisitedLocationDtoOut addVisitedLocationToAnimal(Long animalId, Long locationId) {

        Animal existingAnimal = animalRepository.findJoinedWithAllById(animalId)
                .orElseThrow(() -> new AnimalException(AnimalError.ANIMAL_NOT_FOUND));

        Location existingLocation = locationRepository.findById(locationId)
                .orElseThrow(() -> new LocationException(LocationError.LOCATION_NOT_FOUND));

        if (existingAnimal.getLifeStatus() == AnimalLifeStatus.DEAD) {
            throw new AnimalException(AnimalError.ANIMAL_ALREADY_DEAD);
        }
        if (existingAnimal.getVisitedLocations().isEmpty() &&
                existingAnimal.getChippingLocationId().getLocationId().equals(locationId)) {
            throw new AnimalException(AnimalError.ANIMAL_CHIPPING_LOCATION_ALREADY_EXISTS);
        }

        Optional<VisitedLocation> latterVisitedLocation =
                visitedLocationRepository.findLatterVisitedLocationByAnimalId(animalId);

        if (latterVisitedLocation.isPresent() &&
                latterVisitedLocation.get().getLocation().getLocationId().equals(locationId)) {
            throw new AnimalException(AnimalError.ANIMAL_ALREADY_IN_LOCATION);
        }

        VisitedLocation visitedLocation =
                new VisitedLocation(null, existingAnimal, existingLocation);

        visitedLocationRepository.save(visitedLocation);

        return visitedLocationMapper.toDto(visitedLocation);
    }

    @Override
    @Transactional
    public VisitedLocationDtoOut replaceVisitedLocationInAnimal(Long animalId, UpdateVisitedLocationDto updateVisitedLocationDto) {

        VisitedLocation existingVisitedLocation = visitedLocationRepository
                .findById(updateVisitedLocationDto.getVisitedLocationPointId())
                .orElseThrow(() -> new VisitedLocationException(VisitedLocationError.VISITED_LOCATION_NOT_FOUND));

        Location existingLocation = locationRepository
                .findById(updateVisitedLocationDto.getLocationPointId())
                .orElseThrow(() -> new LocationException(LocationError.LOCATION_NOT_FOUND));

        if (existingVisitedLocation.getLocation().getLocationId()
                .equals(updateVisitedLocationDto.getLocationPointId())) {
            throw new VisitedLocationException(VisitedLocationError.VISITED_LOCATION_EQUALS_LOCATION);
        }

        Animal existingAnimal = animalRepository.findJoinedWithVisitedLocationById(animalId)
                .orElseThrow(() -> new AnimalException(AnimalError.ANIMAL_NOT_FOUND));

        if (existingAnimal.getChippingLocationId().getLocationId().equals(updateVisitedLocationDto.getLocationPointId())) {
            throw new AnimalException(AnimalError.ANIMAL_CHIPPING_LOCATION_ALREADY_EXISTS);
        }

        if (!existingAnimal.getVisitedLocations().contains(existingVisitedLocation)) {
            throw new VisitedLocationException(VisitedLocationError.VISITED_LOCATION_NOT_FOUND);
        }

        existingVisitedLocation.setLocation(existingLocation);

        visitedLocationRepository.save(existingVisitedLocation);

        return visitedLocationMapper.toDto(existingVisitedLocation);
    }


    @Override
    @Transactional
    public void removeVisitedLocationFromAnimal(Long animalId, Long visitedLocationId) {

        VisitedLocation existingVisitedLocation = visitedLocationRepository
                .findById(visitedLocationId)
                .orElseThrow(() -> new VisitedLocationException(VisitedLocationError.VISITED_LOCATION_NOT_FOUND));

        Animal existingAnimal = animalRepository.findJoinedWithVisitedLocationById(animalId)
                .orElseThrow(() -> new AnimalException(AnimalError.ANIMAL_NOT_FOUND));

//        if (existingAnimal.getChippingLocationId().getLocationId().equals(visitedLocationId)) {
//            throw new AnimalException(AnimalError.ANIMAL_CHIPPING_LOCATION_ALREADY_EXISTS);
//        }

        if (!existingAnimal.getVisitedLocations().contains(existingVisitedLocation)) {
            throw new VisitedLocationException(VisitedLocationError.VISITED_LOCATION_NOT_FOUND);
        }

        visitedLocationRepository.deleteById(visitedLocationId);
    }
}
