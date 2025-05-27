package org.example.animalchipization.service.animalTypeRelation.impl;

import org.example.animalchipization.dto.animal.AnimalDtoOut;
import org.example.animalchipization.dto.animal.UpdateAnimalTypeDto;
import org.example.animalchipization.entities.Animal;
import org.example.animalchipization.entities.AnimalType;
import org.example.animalchipization.enums.errors.AnimalError;
import org.example.animalchipization.exception.entities.AnimalException;
import org.example.animalchipization.mappers.animal.AnimalMapper;
import org.example.animalchipization.repository.*;
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
    private final AnimalTypeRepository animalTypeRepository;
    private final AnimalMapper animalMapper;

    @Autowired
    public AnimalTypeRelationsServiceImpl(AnimalRepository animalRepository, AnimalTypeRepository animalTypeRepository, AnimalMapper animalMapper) {
        this.animalRepository = animalRepository;
        this.animalTypeRepository = animalTypeRepository;
        this.animalMapper = animalMapper;
    }
    
    @Override
    @Transactional
    public AnimalDtoOut addTypeToAnimal(Long animalId, Long animalTypeId) {

        Animal existingAnimal = animalRepository.findJoinedWithAllById(animalId)
                .orElseThrow(() -> new AnimalException(AnimalError.ANIMAL_NOT_FOUND));

        AnimalType existingType = animalTypeRepository.findById(animalTypeId)
                .orElseThrow(() -> new AnimalException(AnimalError.ANIMAL_TYPE_NOT_FOUND));

        Set<AnimalType> existingAnimalTypes = existingAnimal.getAnimalTypes();

        if (existingAnimalTypes.contains(existingType)) {
            throw new AnimalException(AnimalError.ANIMAL_TYPES_DUPLICATES);
        }

        existingAnimalTypes.add(existingType);

        existingAnimal.setAnimalTypes(existingAnimalTypes);

        animalRepository.save(existingAnimal);

        return animalMapper.toDto(existingAnimal);

    }

    @Override
    @Transactional
    public AnimalDtoOut replaceTypeInAnimal(Long animalId, UpdateAnimalTypeDto updateAnimalTypeDto) {

        Animal existingAnimal = animalRepository.findJoinedWithAllById(animalId)
                .orElseThrow(() -> new AnimalException(AnimalError.ANIMAL_NOT_FOUND));

        AnimalType existingType = animalTypeRepository.findById(updateAnimalTypeDto.getOldTypeId())
                .orElseThrow(() -> new AnimalException(AnimalError.ANIMAL_TYPE_NOT_FOUND));

        AnimalType newType = animalTypeRepository.findById(updateAnimalTypeDto.getNewTypeId())
                .orElseThrow(() -> new AnimalException(AnimalError.ANIMAL_TYPE_NOT_FOUND));

        Set<AnimalType> existingAnimalTypes = existingAnimal.getAnimalTypes();

        if (!existingAnimalTypes.contains(existingType)) {
            throw new AnimalException(AnimalError.ANIMAL_TYPE_NOT_FOUND);
        }
        if (existingAnimalTypes.contains(newType)) {
            throw new AnimalException(AnimalError.ANIMAL_TYPES_DUPLICATES);
        }

        existingAnimalTypes.remove(existingType);
        existingAnimalTypes.add(newType);

        existingAnimal.setAnimalTypes(existingAnimalTypes);

        animalRepository.save(existingAnimal);

        return animalMapper.toDto(existingAnimal);
    }

    @Override
    @Transactional
    public AnimalDtoOut removeTypeFromAnimal(Long animalId, Long animalTypeId) {

        Animal existingAnimal = animalRepository.findJoinedWithAllById(animalId)
                .orElseThrow(() -> new AnimalException(AnimalError.ANIMAL_NOT_FOUND));

        AnimalType existingType = animalTypeRepository.findById(animalTypeId)
                .orElseThrow(() -> new AnimalException(AnimalError.ANIMAL_TYPE_NOT_FOUND));

        Set<AnimalType> existingAnimalTypes = existingAnimal.getAnimalTypes();

        if (!existingAnimalTypes.contains(existingType)) {
            throw new AnimalException(AnimalError.ANIMAL_TYPE_NOT_FOUND);
        }

        existingAnimalTypes.remove(existingType);

        existingAnimal.setAnimalTypes(existingAnimalTypes);

        animalRepository.save(existingAnimal);

        return animalMapper.toDto(existingAnimal);
    }
}
