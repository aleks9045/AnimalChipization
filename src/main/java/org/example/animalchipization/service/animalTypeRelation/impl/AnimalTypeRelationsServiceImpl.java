package org.example.animalchipization.service.animalTypeRelation.impl;

import org.example.animalchipization.dto.animal.AnimalDtoOut;
import org.example.animalchipization.dto.animalType.UpdateAnimalTypeDto;
import org.example.animalchipization.entities.Animal;
import org.example.animalchipization.entities.AnimalType;
import org.example.animalchipization.mappers.animal.AnimalMapper;
import org.example.animalchipization.repository.*;
import org.example.animalchipization.service.animal.impl.AnimalValidatorImpl;
import org.example.animalchipization.service.animalType.impl.AnimalTypeValidatorImpl;
import org.example.animalchipization.service.animalTypeRelation.AnimalTypeRelationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

/**
 * @author Aleksey
 */
@Service
public class AnimalTypeRelationsServiceImpl implements AnimalTypeRelationsService {
    private final AnimalRepository animalRepository;
    private final AnimalMapper animalMapper;
    private final AnimalValidatorImpl animalValidator;
    private final AnimalTypeValidatorImpl animalTypeValidator;

    @Autowired
    public AnimalTypeRelationsServiceImpl(AnimalRepository animalRepository, AnimalMapper animalMapper, AnimalValidatorImpl animalValidator, AnimalTypeValidatorImpl animalTypeValidator) {
        this.animalRepository = animalRepository;
        this.animalMapper = animalMapper;
        this.animalValidator = animalValidator;
        this.animalTypeValidator = animalTypeValidator;
    }
    
    @Override
    @Transactional
    public AnimalDtoOut addTypeToAnimal(Long animalId, Long animalTypeId) {

        Animal existingAnimal = animalValidator.validateAndGetById(animalId);

        AnimalType existingType = animalTypeValidator.validateAndGetById(animalTypeId);

        Set<AnimalType> existingAnimalTypes = existingAnimal.getAnimalTypes();

        animalTypeValidator.checkTypesDuplicatesType(existingAnimalTypes, existingType);

        existingAnimalTypes.add(existingType);

        existingAnimal.setAnimalTypes(existingAnimalTypes);

        animalRepository.save(existingAnimal);

        return animalMapper.toDto(existingAnimal);

    }

    @Override
    @Transactional
    public AnimalDtoOut replaceTypeInAnimal(Long animalId, UpdateAnimalTypeDto updateAnimalTypeDto) {

        Animal existingAnimal = animalValidator.validateAndGetById(animalId);

        AnimalType existingType = animalTypeValidator.validateAndGetById(
                updateAnimalTypeDto.getOldTypeId());

        AnimalType newType = animalTypeValidator.validateAndGetById(
                updateAnimalTypeDto.getNewTypeId());

        Set<AnimalType> existingAnimalTypes = existingAnimal.getAnimalTypes();

        animalTypeValidator.validateTypes(existingAnimalTypes, existingType);
        animalTypeValidator.checkTypesDuplicatesType(existingAnimalTypes, newType);

        existingAnimalTypes.remove(existingType);
        existingAnimalTypes.add(newType);

        existingAnimal.setAnimalTypes(existingAnimalTypes);

        animalRepository.save(existingAnimal);

        return animalMapper.toDto(existingAnimal);
    }

    @Override
    @Transactional
    public AnimalDtoOut removeTypeFromAnimal(Long animalId, Long animalTypeId) {

        Animal existingAnimal = animalValidator.validateAndGetById(animalId);

        AnimalType existingType = animalTypeValidator.validateAndGetById(animalTypeId);

        Set<AnimalType> existingAnimalTypes = existingAnimal.getAnimalTypes();

        animalTypeValidator.validateTypes(existingAnimalTypes, existingType);

        existingAnimalTypes.remove(existingType);

        existingAnimal.setAnimalTypes(existingAnimalTypes);

        animalRepository.save(existingAnimal);

        return animalMapper.toDto(existingAnimal);
    }
}
