package org.example.animalchipization.service.animal;

import org.example.animalchipization.dto.animal.AnimalDtoUpdate;
import org.example.animalchipization.entity.Animal;
import org.example.animalchipization.entity.AnimalType;
import org.example.animalchipization.entity.Location;
import org.example.animalchipization.service.Validator;

import java.util.List;
import java.util.Set;

/**
 * @author Aleksey
 */
public interface AnimalValidator extends Validator<Animal> {

    void checkAlive(Animal animal);

    void checkVisitedLocationAddition(Animal animal, Location locationId);

    void checkChipperExistence(Integer chipperId);

    void checkChippingLocationExistence(Long locationId);

    Set<AnimalType> validateAndGetAnimalTypes(List<Long> animalTypeSet);

    void checkAnimalUpdate(Animal animal, AnimalDtoUpdate animalDtoUpdate);

    void checkVisitedLocationsEmpty(Long animalId);

    void setDeathTimeIfDead(Animal animal);
}
