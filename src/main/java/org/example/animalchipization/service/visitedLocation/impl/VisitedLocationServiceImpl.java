package org.example.animalchipization.service.visitedLocation.impl;

import jakarta.persistence.criteria.JoinType;
import lombok.RequiredArgsConstructor;
import org.example.animalchipization.dto.location.VisitedLocationSearchCriteria;
import org.example.animalchipization.dto.visitedLocation.UpdateVisitedLocationDto;
import org.example.animalchipization.dto.visitedLocation.VisitedLocationDtoOut;
import org.example.animalchipization.entities.*;
import org.example.animalchipization.mappers.VisitedLocationMapper;
import org.example.animalchipization.repository.VisitedLocationRepository;
import org.example.animalchipization.service.animal.AnimalValidator;
import org.example.animalchipization.service.location.LocationValidator;
import org.example.animalchipization.service.visitedLocation.VisitedLocationService;
import org.example.animalchipization.service.JpaSpecificationBuilder;
import org.example.animalchipization.service.visitedLocation.VisitedLocationValidator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Aleksey
 */
@Service
@RequiredArgsConstructor
public class VisitedLocationServiceImpl implements VisitedLocationService {

    private final VisitedLocationRepository visitedLocationRepository;
    private final VisitedLocationMapper visitedLocationMapper;
    private final AnimalValidator animalValidator;
    private final LocationValidator locationValidator;
    private final VisitedLocationValidator visitedLocationValidator;



    @Override
    @Transactional
    public List<VisitedLocationDtoOut> searchLocations(
            VisitedLocationSearchCriteria VisitedLocationSearchCriteria,
            Pageable pageable) {

        Specification<VisitedLocation> spec = Specification.where(
                JpaSpecificationBuilder.<VisitedLocation>join(
                        VisitedLocation_.ANIMAL,
                        Animal_.ANIMAL_ID,
                        VisitedLocationSearchCriteria.animalId(),
                        JoinType.INNER)
        ).and(
                JpaSpecificationBuilder.dateTimeFrom(
                        VisitedLocation_.DATE_TIME_OF_VISIT_LOCATION_POINT,
                        VisitedLocationSearchCriteria.startDateTime())
        ).and(
                JpaSpecificationBuilder.dateTimeTo(
                        VisitedLocation_.DATE_TIME_OF_VISIT_LOCATION_POINT,
                        VisitedLocationSearchCriteria.endDateTime())
        );

        Page<VisitedLocation> visitedLocationPage = visitedLocationRepository.findAll(spec, pageable);

        return visitedLocationPage.map(visitedLocationMapper::toDto).getContent();
    }

    @Override
    @Transactional
    public VisitedLocationDtoOut addVisitedLocationToAnimal(Long animalId, Long locationId) {

        Animal existingAnimal = visitedLocationValidator.validateAndGetAnimalWithAllLocations(animalId);

        Location existingLocation = locationValidator.validateAndGetById(locationId);

        animalValidator.checkAlive(existingAnimal);
        animalValidator.checkVisitedLocationAddition(existingAnimal, existingLocation);

        visitedLocationValidator.checkLatterFromAnimal(existingAnimal, existingLocation);

        VisitedLocation visitedLocation =
                new VisitedLocation(null, existingAnimal, existingLocation);

        visitedLocationRepository.save(visitedLocation);

        return visitedLocationMapper.toDto(visitedLocation);
    }

    @Override
    @Transactional
    public VisitedLocationDtoOut replaceVisitedLocationInAnimal(
            Long animalId,
            UpdateVisitedLocationDto updateVisitedLocationDto) {

        Location existingLocation = locationValidator.validateAndGetById(
                updateVisitedLocationDto.getLocationPointId()
        );

        Animal existingAnimal = visitedLocationValidator.validateAndGetAnimalWithAllLocations(animalId);

        VisitedLocation existingVisitedLocation = visitedLocationValidator.validateAndGetFromAnimal(
                existingAnimal,
                updateVisitedLocationDto.getVisitedLocationPointId(),
                existingLocation);

        visitedLocationValidator.checkVisitedLocationEqualsLocation(existingVisitedLocation, existingLocation);

        existingVisitedLocation.setLocation(existingLocation);

        visitedLocationRepository.save(existingVisitedLocation);

        return visitedLocationMapper.toDto(existingVisitedLocation);
    }


    @Override
    @Transactional
    public void removeVisitedLocationFromAnimal(Long animalId, Long visitedLocationId) {

        Animal existingAnimal = visitedLocationValidator.validateAndGetAnimalWithVisitedLocations(animalId);

        VisitedLocation visitedLocation = visitedLocationValidator.checkAndGetRemoval(
                existingAnimal, visitedLocationId);

        existingAnimal.getVisitedLocations().remove(visitedLocation);
        visitedLocation.setAnimal(null);

    }
}
