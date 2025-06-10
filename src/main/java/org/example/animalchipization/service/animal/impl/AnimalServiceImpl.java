package org.example.animalchipization.service.animal.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.example.animalchipization.dto.animal.*;
import org.example.animalchipization.entity.*;
import org.example.animalchipization.mapper.animal.AnimalMapper;
import org.example.animalchipization.repository.*;
import org.example.animalchipization.service.JpaSpecificationBuilder;
import org.example.animalchipization.service.animal.AnimalService;
import org.example.animalchipization.service.animal.AnimalValidator;
import org.example.animalchipization.service.location.LocationValidator;
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
    private final EntityManager entityManager;


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
    public List<AnimalDtoOut> searchAnimals(AnimalSearchCriteria animalSearchCriteria, int limit, int offset) {

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

        var cb = entityManager.getCriteriaBuilder();

        var criteriaQuery = cb.createQuery(Animal.class);
        var root = criteriaQuery.from(Animal.class);
        var predicate = spec.toPredicate(root, criteriaQuery, cb);

        if (predicate == null) criteriaQuery.select(root);
        else criteriaQuery.where(predicate);
        criteriaQuery.orderBy(cb.asc(root.get(Animal_.ANIMAL_ID)));

        var typedQuery = entityManager.createQuery(criteriaQuery)
                .setFirstResult(offset)
                .setMaxResults(limit);

        var animals = typedQuery.getResultStream();

        return animals.map(animalMapper::toDto).toList();
    }

}
