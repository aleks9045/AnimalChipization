package org.example.animalchipization.service.animal.impl;

import jakarta.persistence.criteria.JoinType;
import lombok.RequiredArgsConstructor;
import org.example.animalchipization.dto.animal.*;
import org.example.animalchipization.entities.*;
import org.example.animalchipization.mappers.animal.AnimalMapper;
import org.example.animalchipization.repository.*;
import org.example.animalchipization.service.JpaSpecificationBuilder;
import org.example.animalchipization.service.animal.AnimalService;
import org.example.animalchipization.service.animal.AnimalValidator;
import org.example.animalchipization.service.location.LocationValidator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Set;

/**
 * @author Aleksey
 */
@Service
@RequiredArgsConstructor
public class AnimalServiceImpl implements AnimalService {

    private final AnimalRepository animalRepository;
    private final AnimalMapper animalMapper;
    private final AnimalValidator animalValidator;
    private final LocationValidator locationValidator;



    @Override
    @Transactional
    public AnimalDtoOut getAnimal(Long animalId) {

        Animal animal = animalValidator.validateAndGetById(animalId);

        return animalMapper.toDto(animal);
    }

    @Override
    @Transactional
    public AnimalDtoOut addAnimal(AnimalDtoIn animalDtoIn) {

        animalValidator.checkChipperExistence(animalDtoIn.getChipperId());

        locationValidator.checkExistence(animalDtoIn.getChippingLocationId());

        Set<AnimalType> animalTypes = animalValidator.validateAndGetAnimalTypes(animalDtoIn.getAnimalTypes());

        Animal animal = animalMapper.toEntity(animalDtoIn);
        animal.setAnimalTypes(animalTypes);

        animalRepository.save(animal);

        return animalMapper.toDto(animal);
    }

    @Override
    @Transactional
    public AnimalDtoOut updateAnimal(Long animalId, AnimalDtoUpdate animalDtoUpdate) {

        Animal existingAnimal = animalValidator.validateAndGetById(animalId);

        animalValidator.checkChipperExistence(animalDtoUpdate.getChipperId());
        animalValidator.checkChippingLocationExistence(animalDtoUpdate.getChippingLocationId());

        animalValidator.checkAnimalUpdate(existingAnimal, animalDtoUpdate);

        animalMapper.updateEntity(existingAnimal, animalDtoUpdate);

        animalValidator.setDeathTimeIfDead(existingAnimal);

        animalRepository.save(existingAnimal);

        return animalMapper.toDto(existingAnimal);

    }

    @Override
    public void deleteAnimalById(Long animalId) {

        animalValidator.checkExistence(animalId);

        animalValidator.checkVisitedLocationsEmpty(animalId);

        animalRepository.deleteById(animalId);
    }

    @Override
    @Transactional
    public List<AnimalDtoOut> searchAnimals(AnimalSearchCriteria animalSearchCriteria, Pageable pageable) {

        Specification<Animal> spec = Specification.where(
                JpaSpecificationBuilder.<Animal>dateTimeFrom(
                        Animal_.CHIPPING_DATE_TIME,
                        animalSearchCriteria.startDateTime())
        ).and(
                JpaSpecificationBuilder.dateTimeTo(
                        Animal_.CHIPPING_DATE_TIME,
                        animalSearchCriteria.endDateTime())
        ).and(
                JpaSpecificationBuilder.join(
                        Animal_.CHIPPER_ID,
                        Account_.ACCOUNT_ID,
                        animalSearchCriteria.chipperId(),
                        JoinType.INNER)
        ).and(
                JpaSpecificationBuilder.join(
                        Animal_.CHIPPING_LOCATION_ID,
                        Location_.LOCATION_ID,
                        animalSearchCriteria.chippingLocationId(),
                        JoinType.INNER)
        ).and(
                JpaSpecificationBuilder.equal(
                        Animal_.LIFE_STATUS,
                        animalSearchCriteria.lifeStatus())
        ).and(
                JpaSpecificationBuilder.equal(
                        Animal_.GENDER,
                        animalSearchCriteria.gender())
        );
        Page<Animal> animalPage = animalRepository.findAll(spec, pageable);


        return animalPage.map(animalMapper::toDto).getContent();
    }

}
