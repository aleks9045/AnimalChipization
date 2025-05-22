package org.example.animalchipization.service.animalType;

import org.example.animalchipization.dto.animalType.AnimalTypeDtoIn;
import org.example.animalchipization.dto.animalType.AnimalTypeDtoOut;
import org.example.animalchipization.entities.AnimalType;
import org.example.animalchipization.service.animalType.impl.AnimalTypeServiceImpl;

/**
 *  A set of methods for business logic with {@link AnimalType} entity
 *
 *  @author Aleksey
 *
 *  @see AnimalTypeServiceImpl Implementation of this interface
 */
public interface AnimalTypeService {
    /**
     * Gets animalType entry from database
     *
     * @param animalTypeId animalType id
     * @return {@link AnimalTypeDtoOut}
     */
    AnimalTypeDtoOut getAnimalType(Long animalTypeId);


    /**
     * Adds animalType entry from database
     *
     * @param animalTypeDtoIn animalType schema {@link AnimalTypeDtoIn}
     */
    AnimalTypeDtoOut addAnimalType(AnimalTypeDtoIn animalTypeDtoIn);

    /**
     * Updates animalType entry in database
     *
     * @param animalTypeId animalType id
     * @param animalTypeDtoIn {@link AnimalTypeDtoIn}
     * @return {@link AnimalTypeDtoOut}
     */
    AnimalTypeDtoOut updateAnimalType(Long animalTypeId, AnimalTypeDtoIn animalTypeDtoIn);

    /**
     * Deletes animalType entry from database
     *
     * @param animalTypeId animalType id
     */
    void deleteAnimalTypeById(Long animalTypeId);

}
