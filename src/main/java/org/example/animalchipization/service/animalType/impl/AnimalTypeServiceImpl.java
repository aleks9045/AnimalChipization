package org.example.animalchipization.service.animalType.impl;

import org.example.animalchipization.dto.animalType.AnimalTypeDtoIn;
import org.example.animalchipization.dto.animalType.AnimalTypeDtoOut;
import org.example.animalchipization.entities.AnimalType;
import org.example.animalchipization.enums.errors.AnimalTypeError;
import org.example.animalchipization.exception.entities.AnimalTypeException;
import org.example.animalchipization.mappers.AnimalTypeMapper;
import org.example.animalchipization.repository.AnimalTypeRepository;
import org.example.animalchipization.service.animalType.AnimalTypeService;
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

    @Autowired
    public AnimalTypeServiceImpl(AnimalTypeRepository animalTypeRepository, AnimalTypeMapper animalTypeMapper) {
        this.animalTypeRepository = animalTypeRepository;
        this.animalTypeMapper = animalTypeMapper;
    }


    @Override
    public AnimalTypeDtoOut getAnimalType(Long animalTypeId) {

        AnimalType animalType = animalTypeRepository.findById(animalTypeId)
                .orElseThrow(() -> new AnimalTypeException(AnimalTypeError.ANIMAL_TYPE_NOT_FOUND));

        return animalTypeMapper.toDto(animalType);
    }

    @Override
    @Transactional
    public AnimalTypeDtoOut addAnimalType(AnimalTypeDtoIn animalTypeDtoIn) {
        if (animalTypeRepository.existsAnimalTypeByType(animalTypeDtoIn.getType())) {
            throw new AnimalTypeException(AnimalTypeError.ANIMAL_TYPE_ALREADY_EXISTS);
        }

        AnimalType animalType = animalTypeMapper.toEntity(animalTypeDtoIn);
        animalTypeRepository.save(animalType);

        return animalTypeMapper.toDto(animalType);
    }

    @Override
    @Transactional
    public AnimalTypeDtoOut updateAnimalType(Long animalTypeId, AnimalTypeDtoIn animalTypeDtoIn) {
        if (animalTypeRepository.existsAnimalTypeByType(animalTypeDtoIn.getType())) {
            throw new AnimalTypeException(AnimalTypeError.ANIMAL_TYPE_ALREADY_EXISTS);
        }

        AnimalType animalType = animalTypeMapper.toEntity(animalTypeDtoIn);
        animalType.setAnimalTypeId(animalTypeId);
        animalTypeRepository.save(animalType);

        return animalTypeMapper.toDto(animalType);
    }

    @Override
    public void deleteAnimalTypeById(Long animalTypeId) {

        animalTypeRepository.findById(animalTypeId)
                .orElseThrow(() -> new AnimalTypeException(AnimalTypeError.ANIMAL_TYPE_NOT_FOUND));

        animalTypeRepository.deleteById(animalTypeId);
    }
}
