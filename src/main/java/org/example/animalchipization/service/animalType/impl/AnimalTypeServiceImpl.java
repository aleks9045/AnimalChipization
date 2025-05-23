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
    public AnimalTypeDtoOut addAnimalType(AnimalTypeDtoIn animalTypeDtoIn) {

        AnimalType animalType = animalTypeMapper.toEntity(animalTypeDtoIn);
        try {

            AnimalType savedAnimalType = animalTypeRepository.save(animalType);
            return animalTypeMapper.toDto(savedAnimalType);

        } catch (Exception e) {
            throw new AnimalTypeException(AnimalTypeError.ANIMAL_TYPE_ALREADY_EXISTS);
        }
    }

    @Override
    public AnimalTypeDtoOut updateAnimalType(Long animalTypeId, AnimalTypeDtoIn animalTypeDtoIn) {

        AnimalType animalType = animalTypeMapper.toEntity(animalTypeDtoIn);
        animalType.setAnimalTypeId(animalTypeId);
        try {

            AnimalType savedAnimalType = animalTypeRepository.save(animalType);
            return animalTypeMapper.toDto(savedAnimalType);

        } catch (Exception e) {
            throw new AnimalTypeException(AnimalTypeError.ANIMAL_TYPE_ALREADY_EXISTS);
        }
    }

    @Override
    public void deleteAnimalTypeById(Long animalTypeId) {
        animalTypeRepository.deleteById(animalTypeId);
    }
}
