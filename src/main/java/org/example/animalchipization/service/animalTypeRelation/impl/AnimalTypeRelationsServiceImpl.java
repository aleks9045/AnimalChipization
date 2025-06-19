package org.example.animalchipization.service.animalTypeRelation.impl;

import lombok.RequiredArgsConstructor;
import org.example.animalchipization.dto.animal.AnimalDtoOut;
import org.example.animalchipization.dto.animalType.UpdateAnimalTypeDto;
import org.example.animalchipization.entity.Animal;
import org.example.animalchipization.entity.AnimalType;
import org.example.animalchipization.mapper.animal.AnimalMapper;
import org.example.animalchipization.repository.*;
import org.example.animalchipization.service.animal.impl.AnimalValidatorImpl;
import org.example.animalchipization.service.animalType.impl.AnimalTypeValidatorImpl;
import org.example.animalchipization.service.animalTypeRelation.AnimalTypeRelationsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

/**
 * @author Aleksey
 */
@Service
@RequiredArgsConstructor
public class AnimalTypeRelationsServiceImpl implements AnimalTypeRelationsService {
    private final AnimalRepository animalRepository;
    private final AnimalMapper animalMapper;
    private final AnimalValidatorImpl animalValidator;
    private final AnimalTypeValidatorImpl animalTypeValidator;

    
    @Override
    @Transactional
    public AnimalDtoOut addTypeToAnimal(Long animalId, Long animalTypeId) {

        var existingAnimal = animalValidator.validateAndGetById(animalId);

        var existingType = animalTypeValidator.validateAndGetById(animalTypeId);

        var existingAnimalTypes = existingAnimal.getAnimalTypes();

        animalTypeValidator.checkTypesDuplicatesType(existingAnimalTypes, existingType);

        existingAnimalTypes.add(existingType);

        existingAnimal.setAnimalTypes(existingAnimalTypes);

        animalRepository.save(existingAnimal);

        return animalMapper.toDto(existingAnimal);

    }

    @Override
    @Transactional
    public AnimalDtoOut replaceTypeInAnimal(Long animalId, UpdateAnimalTypeDto updateAnimalTypeDto) {

        var existingAnimal = animalValidator.validateAndGetById(animalId);

        var existingType = animalTypeValidator.validateAndGetById(
                updateAnimalTypeDto.getOldTypeId());

        var newType = animalTypeValidator.validateAndGetById(
                updateAnimalTypeDto.getNewTypeId());

        var existingAnimalTypes = existingAnimal.getAnimalTypes();

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

        var existingAnimal = animalValidator.validateAndGetById(animalId);

        var existingType = animalTypeValidator.validateAndGetById(animalTypeId);

        var existingAnimalTypes = existingAnimal.getAnimalTypes();

        animalTypeValidator.validateTypes(existingAnimalTypes, existingType);

        existingAnimalTypes.remove(existingType);

        existingAnimal.setAnimalTypes(existingAnimalTypes);

        animalRepository.save(existingAnimal);

        return animalMapper.toDto(existingAnimal);
    }
}
