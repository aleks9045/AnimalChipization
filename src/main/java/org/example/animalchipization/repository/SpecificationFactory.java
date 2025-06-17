package org.example.animalchipization.repository;


import jakarta.persistence.criteria.JoinType;
import org.example.animalchipization.dto.animal.AnimalSearchCriteria;
import org.example.animalchipization.dto.location.VisitedLocationSearchCriteria;
import org.example.animalchipization.entity.*;
import org.springframework.data.jpa.domain.Specification;

/**
 * @author Aleksey
 */
public class SpecificationFactory {

    public static Specification<Animal> buildAnimalSearchSpec(AnimalSearchCriteria animalSearchCriteria) {

        return Specification.where(
                JpaSpecificationBuilder.<Animal>dateTimeFrom(
                        Animal_.CHIPPING_DATE_TIME,
                        animalSearchCriteria.startDateTime())
        ).and(
                JpaSpecificationBuilder.dateTimeTo(
                        Animal_.CHIPPING_DATE_TIME,
                        animalSearchCriteria.endDateTime())
        ).and(
                JpaSpecificationBuilder.join(
                        Animal_.CHIPPER_ID,
                        Account_.ACCOUNT_ID,
                        animalSearchCriteria.chipperId(),
                        JoinType.INNER)
        ).and(
                JpaSpecificationBuilder.join(
                        Animal_.CHIPPING_LOCATION_ID,
                        Location_.LOCATION_ID,
                        animalSearchCriteria.chippingLocationId(),
                        JoinType.INNER)
        ).and(
                JpaSpecificationBuilder.equal(
                        Animal_.LIFE_STATUS,
                        animalSearchCriteria.lifeStatus())
        ).and(
                JpaSpecificationBuilder.equal(
                        Animal_.GENDER,
                        animalSearchCriteria.gender())
        );
    }

    public static Specification<VisitedLocation> buildVisitedLocationSearchSpec(VisitedLocationSearchCriteria visitedLocationSearchCriteria) {

        return Specification.where(
                JpaSpecificationBuilder.<VisitedLocation>join(
                        VisitedLocation_.ANIMAL,
                        Animal_.ANIMAL_ID,
                        visitedLocationSearchCriteria.animalId(),
                        JoinType.INNER)
        ).and(
                JpaSpecificationBuilder.dateTimeFrom(
                        VisitedLocation_.DATE_TIME_OF_VISIT_LOCATION_POINT,
                        visitedLocationSearchCriteria.startDateTime())
        ).and(
                JpaSpecificationBuilder.dateTimeTo(
                        VisitedLocation_.DATE_TIME_OF_VISIT_LOCATION_POINT,
                        visitedLocationSearchCriteria.endDateTime())
        );
    }
}