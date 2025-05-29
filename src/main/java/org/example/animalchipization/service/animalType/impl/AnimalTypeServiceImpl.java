package org.example.animalchipization.service.animalType.impl;

import org.example.animalchipization.dto.animalType.AnimalTypeDtoIn;
import org.example.animalchipization.dto.animalType.AnimalTypeDtoOut;
import org.example.animalchipization.entities.AnimalType;
import org.example.animalchipization.enums.errors.AnimalTypeError;
import org.example.animalchipization.exception.entities.AnimalTypeException;
import org.example.animalchipization.mappers.AnimalTypeMapper;
import org.example.animalchipization.repository.AnimalTypeRepository;
import org.example.animalchipization.service.animalType.AnimalTypeService;
import org.example.animalchipization.service.animalType.AnimalTypeValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Aleksey
 */
@Service
public class AnimalTypeServiceImpl implements AnimalTypeService {
    private final AnimalTypeRepository animalTypeRepository;
    private final AnimalTypeMapper animalTypeMapper;
    private final AnimalTypeValidator animalTypeValidator;

    @Autowired
    public AnimalTypeServiceImpl(AnimalTypeRepository animalTypeRepository, AnimalTypeMapper animalTypeMapper, AnimalTypeValidator animalTypeValidator) {
        this.animalTypeRepository = animalTypeRepository;
        this.animalTypeMapper = animalTypeMapper;
        this.animalTypeValidator = animalTypeValidator;
    }


    @Override
    public AnimalTypeDtoOut getAnimalType(Long animalTypeId) {

        AnimalType animalType = animalTypeValidator.validateAndGetAnimalType(animalTypeId);

        return animalTypeMapper.toDto(animalType);
    }

    @Override
    @Transactional
    public AnimalTypeDtoOut addAnimalType(AnimalTypeDtoIn animalTypeDtoIn) {
        animalTypeValidator.checkAnimalTypeExistence(animalTypeDtoIn.getType());

        AnimalType animalType = animalTypeMapper.toEntity(animalTypeDtoIn);
        animalTypeRepository.save(animalType);

        return animalTypeMapper.toDto(animalType);
    }

    @Override
    @Transactional
    public AnimalTypeDtoOut updateAnimalType(Long animalTypeId, AnimalTypeDtoIn animalTypeDtoIn) {

        animalTypeValidator.checkAnimalTypeExistence(animalTypeId);
        animalTypeValidator.checkAnimalTypeExistence(animalTypeDtoIn.getType());

        AnimalType animalType = animalTypeMapper.toEntity(animalTypeDtoIn);
        animalType.setAnimalTypeId(animalTypeId);
        animalTypeRepository.save(animalType);

        return animalTypeMapper.toDto(animalType);
    }

    @Override
    public void deleteAnimalTypeById(Long animalTypeId) {

        animalTypeValidator.checkAnimalTypeExistence(animalTypeId);

        try {
            animalTypeRepository.deleteById(animalTypeId);
        } catch (Exception e) {
            throw new AnimalTypeException(AnimalTypeError.ANIMAL_TYPE_STILL_LINKED);
        }
    }
}
