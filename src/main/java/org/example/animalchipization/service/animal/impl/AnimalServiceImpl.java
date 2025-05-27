package org.example.animalchipization.service.animal.impl;

import org.example.animalchipization.dto.animal.*;
import org.example.animalchipization.entities.Animal;
import org.example.animalchipization.entities.AnimalType;
import org.example.animalchipization.enums.AnimalLifeStatus;
import org.example.animalchipization.enums.errors.AnimalError;
import org.example.animalchipization.exception.entities.AnimalException;
import org.example.animalchipization.mappers.animal.AnimalMapper;
import org.example.animalchipization.repository.*;
import org.example.animalchipization.service.JpaSpecificationBuilder;
import org.example.animalchipization.service.animal.AnimalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Aleksey
 */
@Service
public class AnimalServiceImpl implements AnimalService {

    private final AnimalRepository animalRepository;
    private final AccountRepository accountRepository;
    private final LocationRepository locationRepository;
    private final AnimalTypeRepository animalTypeRepository;
    private final AnimalMapper animalMapper;


    @Autowired
    public AnimalServiceImpl(AnimalRepository animalRepository, AccountRepository accountRepository, AnimalTypeRepository animalTypeRepository, AnimalMapper animalMapper, LocationRepository locationRepository) {
        this.animalRepository = animalRepository;
        this.accountRepository = accountRepository;
        this.animalTypeRepository = animalTypeRepository;
        this.animalMapper = animalMapper;
        this.locationRepository = locationRepository;
    }


    @Override
    @Transactional
    public AnimalDtoOut getAnimal(Long animalId) {

        Animal animal = animalRepository.findJoinedWithAllById(animalId)
                .orElseThrow(() -> new AnimalException(AnimalError.ANIMAL_NOT_FOUND));

        return animalMapper.toDto(animal);
    }

    @Override
    @Transactional
    public AnimalDtoOut addAnimal(AnimalDtoIn animalDtoIn) {

        Set<AnimalType> animalTypes = new HashSet<>(
                animalTypeRepository.findAllById(animalDtoIn.getAnimalTypes())
        );
        if (animalTypes.size() < animalDtoIn.getAnimalTypes().size()) {
            throw new AnimalException(AnimalError.ANIMAL_TYPE_NOT_FOUND);
        }
        if (!accountRepository.existsAccountByAccountId(animalDtoIn.getChipperId())) {
            throw new AnimalException(AnimalError.ANIMAL_CHIPPER_NOT_FOUND);
        }
        if (!locationRepository.existsLocationByLocationId(animalDtoIn.getChippingLocationId())) {
            throw new AnimalException(AnimalError.ANIMAL_CHIPPING_LOCATION_NOT_FOUND);
        }

        Animal animal = animalMapper.toEntity(animalDtoIn);
        animal.setAnimalTypes(animalTypes);
        animalRepository.save(animal);

        return animalMapper.toDto(animal);
    }

    @Override
    @Transactional
    public AnimalDtoOut updateAnimal(Long animalId, AnimalDtoUpdate animalDtoUpdate) {

        Animal existingAnimal = animalRepository.findJoinedWithAllById(animalId)
                .orElseThrow(() -> new AnimalException(AnimalError.ANIMAL_NOT_FOUND));

        if (existingAnimal.getLifeStatus() == AnimalLifeStatus.DEAD &&
                animalDtoUpdate.getLifeStatus() == AnimalLifeStatus.ALIVE) {
            throw new AnimalException(AnimalError.ANIMAL_ALREADY_DEAD);
        }
        if (existingAnimal.getChippingLocationId().getLocationId()
                .equals(animalDtoUpdate.getChippingLocationId())) {
            throw new AnimalException(AnimalError.ANIMAL_CHIPPING_LOCATION_ALREADY_EXISTS);
        }

        if (!accountRepository.existsAccountByAccountId(animalDtoUpdate.getChipperId())) {
            throw new AnimalException(AnimalError.ANIMAL_CHIPPER_NOT_FOUND);
        }
        if (!locationRepository.existsLocationByLocationId(animalDtoUpdate.getChippingLocationId())) {
            throw new AnimalException(AnimalError.ANIMAL_CHIPPING_LOCATION_NOT_FOUND);
        }

        animalMapper.updateEntity(existingAnimal, animalDtoUpdate);

        animalRepository.save(existingAnimal);

        return animalMapper.toDto(existingAnimal);

    }

    @Override
    public void deleteAnimalById(Long animalId) {

        if (!animalRepository.existsById(animalId)) {
            throw new AnimalException(AnimalError.ANIMAL_NOT_FOUND);
        }

        animalRepository.deleteById(animalId);
    }

    @Override
    @Transactional
    public List<AnimalDtoOut> searchAnimals(AnimalSearchCriteria animalSearchCriteria, Pageable pageable) {

        Specification<Animal> spec = Specification.where(
                JpaSpecificationBuilder.<Animal>dateTimeFrom(
                        "chippingDateTime",
                        animalSearchCriteria.startDateTime())
        ).and(
                JpaSpecificationBuilder.dateTimeTo(
                        "chippingDateTime",
                        animalSearchCriteria.endDateTime())
        ).and(
                JpaSpecificationBuilder.equal(
                        "chipperId",
                        animalSearchCriteria.chipperId())
        ).and(
                JpaSpecificationBuilder.equal(
                        "chippingLocationId",
                        animalSearchCriteria.chippingLocationId())
        ).and(
                JpaSpecificationBuilder.equal(
                        "lifeStatus",
                        animalSearchCriteria.lifeStatus())
        ).and(
                JpaSpecificationBuilder.equal(
                        "gender",
                        animalSearchCriteria.gender())
        );
        Page<Animal> animalPage = animalRepository.findAll(spec, pageable);


        return animalPage.map(animalMapper::toDto).getContent();
    }

}
