package org.example.animalchipization.service.animalType;

import org.example.animalchipization.entities.Animal;
import org.example.animalchipization.entities.AnimalType;
import org.example.animalchipization.enums.errors.AnimalError;
import org.example.animalchipization.enums.errors.AnimalTypeError;
import org.example.animalchipization.exception.entities.AnimalException;
import org.example.animalchipization.exception.entities.AnimalTypeException;
import org.example.animalchipization.repository.AnimalTypeRepository;
import org.springframework.stereotype.Component;

/**
 * @author Aleksey
 */
@Component
public class AnimalTypeValidator {


    private final AnimalTypeRepository animalTypeRepository;

    public AnimalTypeValidator(AnimalTypeRepository animalTypeRepository) {
        this.animalTypeRepository = animalTypeRepository;
    }

    public AnimalType validateAndGetAnimalType(Long animalTypeId){
        return animalTypeRepository.findById(animalTypeId)
                .orElseThrow(() -> new AnimalTypeException(AnimalTypeError.ANIMAL_TYPE_NOT_FOUND));
    }

    public void checkAnimalTypeExistence(Long animalTypeId) {
        if (!animalTypeRepository.existsById(animalTypeId)) {
            throw new AnimalTypeException(AnimalTypeError.ANIMAL_TYPE_NOT_FOUND);
        }
    }

    public void checkExistenceByType(String type){
        if (animalTypeRepository.existsAnimalTypeByType(type)) {
            throw new AnimalTypeException(AnimalTypeError.ANIMAL_TYPE_ALREADY_EXISTS);
        }
    }
}
