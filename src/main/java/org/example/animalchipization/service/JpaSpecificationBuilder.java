package org.example.animalchipization.service;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Optional;

/**
 * @author Aleksey
 */
@Component
public class JpaSpecificationBuilder {

    public static <T> Specification<T> likeString(String fieldName, String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(
                        criteriaBuilder.lower(root.get(fieldName)),
                        "%" + value.toLowerCase() + "%");
    }

    public static <T> Specification<T> equal(String fieldName, Object value) {
        if (value == null) return null;
        return (root, query, criteriaBuilder) -> {

            if (value instanceof String) {
                return criteriaBuilder.like(
                        criteriaBuilder.lower(root.get(fieldName)),
                        ((String) value).toLowerCase());
            }
            return criteriaBuilder.equal(root.get(fieldName), value);
        };
    }

    public static <T> Specification<T> dateTimeFrom(String fieldName, Instant value) {
        if (value == null) return null;
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThan(root.get(fieldName), value));
    }

    public static <T> Specification<T> dateTimeTo(String fieldName, Instant value) {
        if (value == null) return null;
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.lessThan(root.get(fieldName), value));
    }

    public static <T> Specification<T> join(String joinFieldName,
                                            String comparisonFieldName,
                                            Object value, JoinType joinType) {
        if (value == null) return null;
        return (root, query, criteriaBuilder) -> {
            Join<?, ?> join = root.join(joinFieldName, joinType);
            return criteriaBuilder.equal(join.get(comparisonFieldName), value);
        };
    }

//    public static <T> Specification<T> joinManyToManyWithDateFilter(
//            String collectionFieldName,
//            String secondFieldName,
//            String comparisonFieldName,
//            Object value,
//            JoinType joinType,
//            String dateFieldName,
//            Instant startDateTime,
//            Instant endDateTime
//    ) {
//        return Specification.where(
//                joinManyToMany(collectionFieldName, secondFieldName, comparisonFieldName, value, joinType)
//        ).and(
//                dateTimeFrom(dateFieldName, startDateTime)
//        ).and(
//                dateTimeTo(dateFieldName, endDateTime)
//        );
//    }
}
