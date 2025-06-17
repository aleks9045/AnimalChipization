package org.example.animalchipization.service.animal;

import org.example.animalchipization.dto.animal.*;
import org.example.animalchipization.entity.Animal;
import org.example.animalchipization.service.animal.impl.AnimalServiceImpl;

import java.util.List;

/**
 *  A set of methods for business logic with {@link Animal} entity
 *
 *  @author Aleksey
 *
 *  @see AnimalServiceImpl Implementation of this interface
 */
public interface AnimalService {
    /**
     * Gets animal entry from database
     *
     * @param animalId animal id
     * @return {@link AnimalDtoOut}
     */
    AnimalDtoOut getAnimal(Long animalId);


    /**
     * Adds animal entry from database
     *
     * @param animalDtoIn animal schema {@link AnimalDtoIn}
     */
    AnimalDtoOut addAnimal(AnimalDtoIn animalDtoIn);

    /**
     * Updates animal entry in database
     *
     * @param animalId animal id
     * @param animalDtoUpdate {@link AnimalDtoUpdate}
     * @return {@link AnimalDtoOut}
     */
    AnimalDtoOut updateAnimal(Long animalId, AnimalDtoUpdate animalDtoUpdate);

    /**
     * Deletes animal entry from database
     *
     * @param animalId animal id
     */
    void deleteAnimalById(Long animalId);

    /**
     * Search animal entries from database
     *
     * @param animalSearchCriteria animal search criteria DTO
     * @param limit limit
     * @param offset offset
     * @return List of Animal DTO for output
     */
    List<AnimalDtoOut> searchAnimals(AnimalSearchCriteria animalSearchCriteria,
                                     int limit, int offset);

}
