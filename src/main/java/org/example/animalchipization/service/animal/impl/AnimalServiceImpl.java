package org.example.animalchipization.service.animal.impl;

import org.example.animalchipization.dto.animal.AnimalDtoIn;
import org.example.animalchipization.dto.animal.AnimalDtoOut;
import org.example.animalchipization.dto.animal.AnimalDtoUpdate;
import org.example.animalchipization.dto.animal.AnimalSearchCriteria;
import org.example.animalchipization.entities.Animal;
import org.example.animalchipization.entities.AnimalType;
import org.example.animalchipization.enums.errors.AnimalError;
import org.example.animalchipization.exception.entities.AnimalException;
import org.example.animalchipization.mappers.animal.AnimalMapper;
import org.example.animalchipization.repository.AnimalRepository;
import org.example.animalchipization.repository.AnimalTypeRepository;
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
    private final AnimalTypeRepository animalTypeRepository;
    private final AnimalMapper animalMapper;
    private final JpaSpecificationBuilder<Animal> jpaSpecificationBuilder;

    @Autowired
    public AnimalServiceImpl(AnimalRepository animalRepository, AnimalTypeRepository animalTypeRepository, AnimalMapper animalMapper, JpaSpecificationBuilder<Animal> jpaSpecificationBuilder) {
        this.animalRepository = animalRepository;
        this.animalTypeRepository = animalTypeRepository;
        this.animalMapper = animalMapper;
        this.jpaSpecificationBuilder = jpaSpecificationBuilder;
    }


    @Override
    @Transactional
    public AnimalDtoOut getAnimal(Long animalId) {

        Animal animal = animalRepository.findById(animalId)
                .orElseThrow(() -> new AnimalException(AnimalError.ANIMAL_NOT_FOUND));

        return animalMapper.toDto(animal);
    }

    @Override
    @Transactional
    public AnimalDtoOut addAnimal(AnimalDtoIn animalDtoIn) {

        Set<AnimalType> animalTypes = new HashSet<>(
                animalTypeRepository.findAllById(animalDtoIn.getAnimalTypes())
        );

        Animal animal = animalMapper.toEntity(animalDtoIn);
        animal.setAnimalTypes(animalTypes);
        Animal savedAnimal = animalRepository.save(animal);

        return animalMapper.toDto(savedAnimal);
    }

    @Override
    @Transactional
    public AnimalDtoOut updateAnimal(Long animalId, AnimalDtoUpdate animalDtoUpdate) {

        Animal existingAnimal = animalRepository.findById(animalId)
                .orElseThrow(() -> new AnimalException(AnimalError.ANIMAL_NOT_FOUND));

        animalMapper.updateEntity(existingAnimal, animalDtoUpdate);

        Animal savedAnimal = animalRepository.save(existingAnimal);

        return animalMapper.toDto(savedAnimal);

    }

    @Override
    public void deleteAnimalById(Long animalId) {
        animalRepository.deleteById(animalId);
    }

    @Override
    @Transactional
    public List<AnimalDtoOut> searchAnimal(AnimalSearchCriteria animalSearchCriteria, Pageable pageable) {

        Specification<Animal> spec = Specification.where(
                jpaSpecificationBuilder.dateTimeFrom(
                        "chippingDateTime",
                        animalSearchCriteria.startDateTime())
        ).and(
                jpaSpecificationBuilder.dateTimeTo(
                        "chippingDateTime",
                        animalSearchCriteria.endDateTime())
        ).and(
                jpaSpecificationBuilder.equal(
                        "chipperId",
                        animalSearchCriteria.chipperId())
        ).and(
                jpaSpecificationBuilder.equal(
                        "chippingLocationId",
                        animalSearchCriteria.chippingLocationId())
        ).and(
                jpaSpecificationBuilder.equal(
                        "lifeStatus",
                        animalSearchCriteria.lifeStatus())
        ).and(
                jpaSpecificationBuilder.equal(
                        "gender",
                        animalSearchCriteria.gender())
        );
        Page<Animal> animalPage = animalRepository.findAll(spec, pageable);


        return animalPage.map(animalMapper::toDto).getContent();
    }
}
