package org.example.animalchipization.service.animal.impl;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import static org.apache.commons.lang3.BooleanUtils.*;
import org.example.animalchipization.dto.animal.AnimalDtoUpdate;
import org.example.animalchipization.entity.*;
import org.example.animalchipization.enums.AnimalLifeStatus;
import org.example.animalchipization.enums.error.BadRequestError;
import org.example.animalchipization.enums.error.NotFoundError;
import org.example.animalchipization.exception.RequestException;
import org.example.animalchipization.repository.*;
import org.example.animalchipization.service.animal.AnimalValidator;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

/**
 * @author Aleksey
 */
@Component
@RequiredArgsConstructor
public class AnimalValidatorImpl implements AnimalValidator {

    private final AnimalRepository animalRepository;
    private final AnimalTypeRepository animalTypeRepository;
    private final AccountRepository accountRepository;
    private final LocationRepository locationRepository;
    private final EntityManager entityManager;


    @Override
    public Animal validateAndGetById(Long animalId) {
        Animal animal = animalRepository.findJoinedWithAllExceptVisitedLocationById(animalId)
                .orElseThrow(() -> new RequestException(NotFoundError.ANIMAL_NOT_FOUND));

        List<VisitedLocation> visitedLocations = animalRepository.findVisitedLocationsByAnimalId(animalId);

        animal.setVisitedLocations(visitedLocations);
        return animal;
    }

    @Override
    public void checkExistence(Long animalId) {
        if (isFalse(animalRepository.existsById(animalId))) {
            throw new RequestException(NotFoundError.ANIMAL_NOT_FOUND);
        }
    }

    @Override
    public void checkAlive(Animal animal) {
        if (animal.getLifeStatus() == AnimalLifeStatus.DEAD) {
            throw new RequestException(BadRequestError.ANIMAL_ALREADY_DEAD);
        }
    }

    @Override
    public void checkVisitedLocationAddition(Animal animal, Location locationId) {
        if (animal.getVisitedLocations().isEmpty() &&
                animal.getChippingLocationId().equals(locationId)) {
            throw new RequestException(BadRequestError.ANIMAL_CHIPPING_LOCATION_ALREADY_EXISTS);
        }
    }

    @Override
    public void checkChipperExistence(Integer chipperId) {
        if (isFalse(accountRepository.existsAccountByAccountId(chipperId))) {
            throw new RequestException(NotFoundError.ANIMAL_CHIPPER_NOT_FOUND);
        }
    }

    @Override
    public void checkChippingLocationExistence(Long locationId) {
        if (isFalse(locationRepository.existsLocationByLocationId(locationId))) {
            throw new RequestException(NotFoundError.ANIMAL_CHIPPING_LOCATION_NOT_FOUND);
        }
    }

    @Override
    public Set<AnimalType> validateAndGetAnimalTypes(List<Long> animalTypeSet) {
        Set<AnimalType> animalTypes = new HashSet<>(
                animalTypeRepository.findAllById(animalTypeSet)
        );
        if (animalTypes.size() < animalTypeSet.size()) {
            throw new RequestException(NotFoundError.ANIMAL_TYPE_NOT_FOUND);
        }
        return animalTypes;
    }

    @Override
    public void checkAnimalUpdate(Animal animal, AnimalDtoUpdate animalDtoUpdate) {

        if (animal.getLifeStatus() == AnimalLifeStatus.DEAD &&
                animalDtoUpdate.getLifeStatus() == AnimalLifeStatus.ALIVE) {
            throw new RequestException(BadRequestError.ANIMAL_ALREADY_DEAD);
        }

        int firstElementIndex = 0;

        if (isFalse(animal.getVisitedLocations().isEmpty()) &&
                // Getting first visited location
                animal.getVisitedLocations().get(firstElementIndex).getLocation().getLocationId()
                        .equals(animalDtoUpdate.getChippingLocationId())) {

            throw new RequestException(BadRequestError.ANIMAL_CHIPPING_LOCATION_ALREADY_EXISTS);
        }
    }

    @Override
    public void checkVisitedLocationsEmpty(Long animalId) {

        if (isFalse(animalRepository.findVisitedLocationsByAnimalId(animalId).isEmpty())) {
            throw new RequestException(BadRequestError.ANIMAL_HAS_LEFT_CHIPPING_LOCATION);
        }
    }

    @Override
    public void setDeathTimeIfDead(Animal animal) {
        if (animal.getLifeStatus() == AnimalLifeStatus.DEAD && animal.getDeathDateTime() == null) {
            animal.setDeathDateTime(Instant.now().truncatedTo(ChronoUnit.MICROS));
        }
    }

    public Stream<Animal> getSearchedAnimalStream(Specification<Animal> spec,
                                                  int limit, int offset) {

        var cb = entityManager.getCriteriaBuilder();

        var criteriaQuery = cb.createQuery(Animal.class);
        var root = criteriaQuery.from(Animal.class);
        var predicate = spec.toPredicate(root, criteriaQuery, cb);

        if (predicate == null) criteriaQuery.select(root);
        else criteriaQuery.where(predicate);
        criteriaQuery.orderBy(cb.asc(root.get(Animal_.ANIMAL_ID)));

        var typedQuery = entityManager.createQuery(criteriaQuery)
                .setFirstResult(offset)
                .setMaxResults(limit);

        return typedQuery.getResultStream();
    }
}
