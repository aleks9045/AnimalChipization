package org.example.animalchipization.service.animalType.impl;


import lombok.RequiredArgsConstructor;
import org.example.animalchipization.entity.AnimalType;
import org.example.animalchipization.enums.error.BadRequestError;
import org.example.animalchipization.enums.error.ConflictError;
import org.example.animalchipization.enums.error.NotFoundError;
import org.example.animalchipization.exception.RequestException;
import org.example.animalchipization.repository.AnimalTypeRepository;
import org.example.animalchipization.service.animalType.AnimalTypeValidator;
import org.springframework.stereotype.Component;

import java.util.Set;

import static org.apache.commons.lang3.BooleanUtils.isFalse;

/**
 * @author Aleksey
 */
@Component
@RequiredArgsConstructor
public class AnimalTypeValidatorImpl implements AnimalTypeValidator {

    private final AnimalTypeRepository animalTypeRepository;


    @Override
    public AnimalType validateAndGetById(Long animalTypeId) {
        return animalTypeRepository.findById(animalTypeId)
                .orElseThrow(() -> new RequestException(NotFoundError.ANIMAL_TYPE_NOT_FOUND));
    }

    @Override
    public void checkExistence(Long animalTypeId) {
        if (isFalse(animalTypeRepository.existsById(animalTypeId))) {
            throw new RequestException(NotFoundError.ANIMAL_TYPE_NOT_FOUND);
        }
    }

    @Override
    public void checkExistence(String type) {
        if (animalTypeRepository.existsAnimalTypeByType(type)) {
            throw new RequestException(ConflictError.ANIMAL_TYPE_ALREADY_EXISTS);
        }
    }

    @Override
    public void validateTypes(Set<AnimalType> animalTypeSet, AnimalType animalType) {
        if (isFalse(animalTypeSet.contains(animalType))) {
            throw new RequestException(NotFoundError.ANIMAL_TYPE_NOT_FOUND);
        }
        if (animalTypeSet.size() == 1) {
            throw new RequestException(BadRequestError.ANIMAL_HAS_ONE_TYPE);
        }
    }

    @Override
    public void checkTypesDuplicatesType(Set<AnimalType> animalTypeSet, AnimalType animalType) {
        if (animalTypeSet.contains(animalType)) {
            throw new RequestException(ConflictError.ANIMAL_TYPES_DUPLICATES);
        }
    }

    @Override
    public void checkLinked(AnimalType animalType) {
        if (animalTypeRepository.existsByAnimalTypeId(animalType.getAnimalTypeId())) {
            throw new RequestException(BadRequestError.ANIMAL_TYPE_STILL_LINKED);
        }
    }
}
