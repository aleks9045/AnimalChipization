package org.example.animalchipization.service.visitedLocation.impl;

import jakarta.persistence.criteria.JoinType;
import org.example.animalchipization.dto.location.VLSearchCriteria;
import org.example.animalchipization.dto.visitedLocation.UpdateVLDto;
import org.example.animalchipization.dto.visitedLocation.VLDtoOut;
import org.example.animalchipization.entities.Animal;
import org.example.animalchipization.entities.Location;
import org.example.animalchipization.entities.VisitedLocation;
import org.example.animalchipization.enums.errors.AnimalError;
import org.example.animalchipization.enums.errors.VLError;
import org.example.animalchipization.exception.entities.AnimalException;
import org.example.animalchipization.exception.entities.VLException;
import org.example.animalchipization.mappers.VisitedLocationMapper;
import org.example.animalchipization.repository.AnimalRepository;
import org.example.animalchipization.repository.LocationRepository;
import org.example.animalchipization.repository.VisitedLocationRepository;
import org.example.animalchipization.service.animal.AnimalValidator;
import org.example.animalchipization.service.location.LocationValidator;
import org.example.animalchipization.service.visitedLocation.VisitedLocationService;
import org.example.animalchipization.service.JpaSpecificationBuilder;
import org.example.animalchipization.service.visitedLocation.VLValidator;
import org.springframework.beans.factory.annotation.Autowired;
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
public class VisitedLocationServiceImpl implements VisitedLocationService {

    private final VisitedLocationRepository visitedLocationRepository;
    private final VisitedLocationMapper visitedLocationMapper;
    private final AnimalRepository animalRepository;
    private final LocationRepository locationRepository;
    private final AnimalValidator animalValidator;
    private final LocationValidator locationValidator;
    private final VLValidator VLValidator;

    @Autowired
    public VisitedLocationServiceImpl(VisitedLocationRepository visitedLocationRepository, VisitedLocationMapper visitedLocationMapper, AnimalRepository animalRepository, LocationRepository locationRepository, AnimalValidator animalValidator, LocationValidator locationValidator, VLValidator VLValidator) {
        this.visitedLocationRepository = visitedLocationRepository;
        this.visitedLocationMapper = visitedLocationMapper;
        this.animalRepository = animalRepository;
        this.locationRepository = locationRepository;
        this.animalValidator = animalValidator;
        this.locationValidator = locationValidator;
        this.VLValidator = VLValidator;
    }

    @Override
    @Transactional
    public List<VLDtoOut> searchLocations(VLSearchCriteria VLSearchCriteria, Pageable pageable) {

        Specification<VisitedLocation> spec = Specification.where(
                JpaSpecificationBuilder.<VisitedLocation>join(
                        "animal",
                        "animalId",
                        VLSearchCriteria.animalId(),
                        JoinType.INNER)
        ).and(
                JpaSpecificationBuilder.dateTimeFrom(
                        "dateTimeOfVisitLocationPoint",
                        VLSearchCriteria.startDateTime())
        ).and(
                JpaSpecificationBuilder.dateTimeTo(
                        "dateTimeOfVisitLocationPoint",
                        VLSearchCriteria.endDateTime())
        );

        Page<VisitedLocation> locationPage = visitedLocationRepository.findAll(spec, pageable);

        return locationPage.map(visitedLocationMapper::toDto).getContent();
    }

    @Override
    @Transactional
    public VLDtoOut addVisitedLocationToAnimal(Long animalId, Long locationId) {

        Animal existingAnimal = animalValidator.validateAndGetAnimal(animalId);

        Location existingLocation = locationValidator.validateAndGetLocation(locationId);

        animalValidator.checkAnimalAlive(existingAnimal);
        animalValidator.checkVLAddition(existingAnimal, locationId);

        VLValidator.checkLatterVL(animalId, locationId);

        VisitedLocation visitedLocation =
                new VisitedLocation(null, existingAnimal, existingLocation);

        visitedLocationRepository.save(visitedLocation);

        return visitedLocationMapper.toDto(visitedLocation);
    }

    @Override
    @Transactional
    public VLDtoOut replaceVisitedLocationInAnimal(Long animalId, UpdateVLDto updateVLDto) {

        VisitedLocation existingVisitedLocation = VLValidator.validateAndGetVL(
                updateVLDto.getVisitedLocationPointId()
        );

        Location existingLocation = locationValidator.validateAndGetLocation(
                updateVLDto.getLocationPointId()
        );

        if (existingVisitedLocation.getLocation().getLocationId()
                .equals(updateVLDto.getLocationPointId())) {
            throw new VLException(VLError.VISITED_LOCATION_EQUALS_LOCATION);
        }

        Animal existingAnimal = VLValidator.validateAndGetAnimal(animalId);

        if (existingAnimal.getChippingLocationId().getLocationId().equals(updateVLDto.getLocationPointId())) {
            throw new AnimalException(AnimalError.ANIMAL_CHIPPING_LOCATION_ALREADY_EXISTS);
        }

        if (!existingAnimal.getVisitedLocations().contains(existingVisitedLocation)) {
            throw new VLException(VLError.VISITED_LOCATION_NOT_FOUND);
        }

        existingVisitedLocation.setLocation(existingLocation);

        visitedLocationRepository.save(existingVisitedLocation);

        return visitedLocationMapper.toDto(existingVisitedLocation);
    }


    @Override
    @Transactional
    public void removeVisitedLocationFromAnimal(Long animalId, Long visitedLocationId) {

        VisitedLocation existingVisitedLocation = VLValidator.validateAndGetVL(visitedLocationId);

        Animal existingAnimal = VLValidator.validateAndGetAnimal(animalId);

//        if (existingAnimal.getChippingLocationId().getLocationId().equals(visitedLocationId)) {
//            throw new AnimalException(AnimalError.ANIMAL_CHIPPING_LOCATION_ALREADY_EXISTS);
//        }

        if (!existingAnimal.getVisitedLocations().contains(existingVisitedLocation)) {
            throw new VLException(VLError.VISITED_LOCATION_NOT_FOUND);
        }

        visitedLocationRepository.deleteById(visitedLocationId);
    }
}
