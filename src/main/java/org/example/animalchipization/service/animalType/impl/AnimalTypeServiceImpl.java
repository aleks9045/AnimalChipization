package org.example.animalchipization.service.animalType.impl;

import lombok.RequiredArgsConstructor;
import org.example.animalchipization.dto.animalType.AnimalTypeDtoIn;
import org.example.animalchipization.dto.animalType.AnimalTypeDtoOut;
import org.example.animalchipization.entities.AnimalType;
import org.example.animalchipization.mappers.AnimalTypeMapper;
import org.example.animalchipization.repository.AnimalTypeRepository;
import org.example.animalchipization.service.animalType.AnimalTypeService;
import org.example.animalchipization.service.animalType.AnimalTypeValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Aleksey
 */
@Service
@RequiredArgsConstructor
public class AnimalTypeServiceImpl implements AnimalTypeService {

    private final AnimalTypeRepository animalTypeRepository;
    private final AnimalTypeMapper animalTypeMapper;
    private final AnimalTypeValidator animalTypeValidator;


    @Override
    public AnimalTypeDtoOut getAnimalType(Long animalTypeId) {

        AnimalType animalType = animalTypeValidator.validateAndGetById(animalTypeId);

        return animalTypeMapper.toDto(animalType);
    }

    @Override
    public AnimalTypeDtoOut addAnimalType(AnimalTypeDtoIn animalTypeDtoIn) {

        animalTypeValidator.checkExistence(animalTypeDtoIn.getType());

        AnimalType animalType = animalTypeMapper.toEntity(animalTypeDtoIn);
        AnimalType savedType = animalTypeRepository.save(animalType);

        return animalTypeMapper.toDto(savedType);
    }

    @Override
    @Transactional
    public AnimalTypeDtoOut updateAnimalType(Long animalTypeId, AnimalTypeDtoIn animalTypeDtoIn) {

        animalTypeValidator.checkExistence(animalTypeId);
        animalTypeValidator.checkExistence(animalTypeDtoIn.getType());

        AnimalType animalType = animalTypeMapper.toEntity(animalTypeDtoIn);
        animalType.setAnimalTypeId(animalTypeId);
        animalTypeRepository.save(animalType);

        return animalTypeMapper.toDto(animalType);
    }

    @Override
    public void deleteAnimalTypeById(Long animalTypeId) {

        AnimalType animalType = animalTypeValidator.validateAndGetById(animalTypeId);

        animalTypeValidator.checkLinked(animalType);

        animalTypeRepository.deleteById(animalTypeId);
    }
}
