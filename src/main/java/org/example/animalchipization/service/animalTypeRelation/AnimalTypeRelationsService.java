package org.example.animalchipization.service.animalTypeRelation;

import org.example.animalchipization.dto.animal.AnimalDtoOut;
import org.example.animalchipization.dto.animalType.UpdateAnimalTypeDto;

/**
 * @author Aleksey
 */
public interface AnimalTypeRelationsService {

    AnimalDtoOut addTypeToAnimal(Long animalId, Long animalTypeId);

    AnimalDtoOut replaceTypeInAnimal(Long animalId, UpdateAnimalTypeDto updateAnimalTypeDto);

    AnimalDtoOut removeTypeFromAnimal(Long animalId, Long animalTypeId);

}
