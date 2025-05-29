package org.example.animalchipization.service.animal;

import org.example.animalchipization.dto.animal.AnimalDtoUpdate;
import org.example.animalchipization.entities.Animal;
import org.example.animalchipization.entities.AnimalType;
import org.example.animalchipization.enums.AnimalLifeStatus;
import org.example.animalchipization.enums.errors.AnimalError;
import org.example.animalchipization.exception.entities.AnimalException;
import org.example.animalchipization.repository.*;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Aleksey
 */
@Component
public class AnimalValidator {
    private final AnimalRepository animalRepository;
    private final AnimalTypeRepository animalTypeRepository;
    private final AccountRepository accountRepository;
    private final LocationRepository locationRepository;


    public AnimalValidator(AnimalRepository animalRepository, AnimalTypeRepository animalTypeRepository, AccountRepository accountRepository, LocationRepository locationRepository) {
        this.animalRepository = animalRepository;
        this.animalTypeRepository = animalTypeRepository;
        this.accountRepository = accountRepository;
        this.locationRepository = locationRepository;
    }

    public Animal validateAndGetAnimal(Long animalId){
        return animalRepository.findJoinedWithAllById(animalId)
                .orElseThrow(() -> new AnimalException(AnimalError.ANIMAL_NOT_FOUND));
    }

    public void checkAnimalExistence(Long animalId) {
        if (!animalRepository.existsById(animalId)) {
            throw new AnimalException(AnimalError.ANIMAL_NOT_FOUND);
        }
    }

    public void checkAnimalAlive(Animal animal) {
        if (animal.getLifeStatus() == AnimalLifeStatus.DEAD) {
            throw new AnimalException(AnimalError.ANIMAL_ALREADY_DEAD);
        }
    }

    public void checkVLAddition(Animal animal, Long locationId) {
        if (animal.getVisitedLocations().isEmpty() &&
                animal.getChippingLocationId().getLocationId().equals(locationId)) {
            throw new AnimalException(AnimalError.ANIMAL_CHIPPING_LOCATION_ALREADY_EXISTS);
        }
    }

    public void checkChipperExistence(Integer chipperId) {
        if (!accountRepository.existsAccountByAccountId(chipperId)) {
            throw new AnimalException(AnimalError.ANIMAL_CHIPPER_NOT_FOUND);
        }
    }

    public void checkChippingLocationExistence(Long locationId) {
        if (!locationRepository.existsLocationByLocationId(locationId)) {
            throw new AnimalException(AnimalError.ANIMAL_CHIPPING_LOCATION_NOT_FOUND);
        }
    }

    public Set<AnimalType> validateAndGetAnimalTypes(List<Long> animalTypeSet){
        Set<AnimalType> animalTypes = new HashSet<>(
                animalTypeRepository.findAllById(animalTypeSet)
        );
        if (animalTypes.size() < animalTypeSet.size()) {
            throw new AnimalException(AnimalError.ANIMAL_TYPE_NOT_FOUND);
        }
        return animalTypes;
    }

    public void checkAnimalUpdate(Animal animal, AnimalDtoUpdate animalDtoUpdate){

        if (animal.getLifeStatus() == AnimalLifeStatus.DEAD &&
                animalDtoUpdate.getLifeStatus() == AnimalLifeStatus.ALIVE) {
            throw new AnimalException(AnimalError.ANIMAL_ALREADY_DEAD);
        }
//        if (animal.getChippingLocationId().getLocationId()
//                .equals(animalDtoUpdate.getChippingLocationId())) {
//            throw new AnimalException(AnimalError.ANIMAL_CHIPPING_LOCATION_ALREADY_EXISTS);
//        }
    }

}
