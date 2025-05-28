package org.example.animalchipization.service.animal.impl;

import org.example.animalchipization.dto.animal.*;
import org.example.animalchipization.entities.Animal;
import org.example.animalchipization.entities.AnimalType;
import org.example.animalchipization.enums.AnimalLifeStatus;
import org.example.animalchipization.enums.errors.AnimalError;
import org.example.animalchipization.exception.entities.AnimalException;
import org.example.animalchipization.mappers.animal.AnimalMapper;
import org.example.animalchipization.repository.*;
import org.example.animalchipization.service.JpaSpecificationBuilder;
import org.example.animalchipization.service.animal.AnimalService;
import org.example.animalchipization.service.animal.AnimalValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Aleksey
 */
@Service
public class AnimalServiceImpl implements AnimalService {

    private final AnimalRepository animalRepository;
    private final AnimalMapper animalMapper;
    private final AnimalValidator animalValidator;

    @Autowired
    public AnimalServiceImpl(AnimalRepository animalRepository, AnimalMapper animalMapper, AnimalValidator animalValidator) {
        this.animalRepository = animalRepository;
        this.animalMapper = animalMapper;
        this.animalValidator = animalValidator;
    }


    @Override
    @Transactional
    public AnimalDtoOut getAnimal(Long animalId) {

        Animal animal = animalValidator.validateAndGetAnimal(animalId);

        return animalMapper.toDto(animal);
    }

    @Override
    @Transactional
    public AnimalDtoOut addAnimal(AnimalDtoIn animalDtoIn) {

        animalValidator.checkChipperExistence(animalDtoIn.getChipperId());

        animalValidator.checkChippingLocationExistence(animalDtoIn.getChippingLocationId());

        Set<AnimalType> animalTypes = animalValidator.validateAndGetAnimalTypes(animalDtoIn.getAnimalTypes());

        Animal animal = animalMapper.toEntity(animalDtoIn);
        animal.setAnimalTypes(animalTypes);
        animalRepository.save(animal);

        return animalMapper.toDto(animal);
    }

    @Override
    @Transactional
    public AnimalDtoOut updateAnimal(Long animalId, AnimalDtoUpdate animalDtoUpdate) {

        Animal existingAnimal = animalValidator.validateAndGetAnimal(animalId);

        animalValidator.checkAnimalUpdate(existingAnimal, animalDtoUpdate);

        animalValidator.checkChipperExistence(animalDtoUpdate.getChipperId());
        animalValidator.checkChippingLocationExistence(animalDtoUpdate.getChippingLocationId());

        animalMapper.updateEntity(existingAnimal, animalDtoUpdate);

        animalRepository.save(existingAnimal);

        return animalMapper.toDto(existingAnimal);

    }

    @Override
    public void deleteAnimalById(Long animalId) {

        animalValidator.checkAnimalExistence(animalId);

        animalRepository.deleteById(animalId);
    }

    @Override
    @Transactional
    public List<AnimalDtoOut> searchAnimals(AnimalSearchCriteria animalSearchCriteria, Pageable pageable) {

        Specification<Animal> spec = Specification.where(
                JpaSpecificationBuilder.<Animal>dateTimeFrom(
                        "chippingDateTime",
                        animalSearchCriteria.startDateTime())
        ).and(
                JpaSpecificationBuilder.dateTimeTo(
                        "chippingDateTime",
                        animalSearchCriteria.endDateTime())
        ).and(
                JpaSpecificationBuilder.equal(
                        "chipperId",
                        animalSearchCriteria.chipperId())
        ).and(
                JpaSpecificationBuilder.equal(
                        "chippingLocationId",
                        animalSearchCriteria.chippingLocationId())
        ).and(
                JpaSpecificationBuilder.equal(
                        "lifeStatus",
                        animalSearchCriteria.lifeStatus())
        ).and(
                JpaSpecificationBuilder.equal(
                        "gender",
                        animalSearchCriteria.gender())
        );
        Page<Animal> animalPage = animalRepository.findAll(spec, pageable);


        return animalPage.map(animalMapper::toDto).getContent();
    }

}
