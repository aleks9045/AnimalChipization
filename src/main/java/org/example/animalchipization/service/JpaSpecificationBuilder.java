package org.example.animalchipization.service;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Optional;

/**
 * @author Aleksey
 */
@Component
public class JpaSpecificationBuilder<T> {

    public Specification<T> likeString(String fieldName, String value) {
        if (value == null) return null;
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.like(
                        criteriaBuilder.lower(root.get(fieldName)),
                        "%" + value.toLowerCase() + "%"));
    }

    public Specification<T> equal(String fieldName, Object value) {
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

    public Specification<T> dateTimeFrom(String fieldName, Instant value) {
        if (value == null) return null;
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThan(root.get(fieldName), value));
    }

    public Specification<T> dateTimeTo(String fieldName, Instant value) {
        if (value == null) return null;
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.lessThan(root.get(fieldName), value));
    }
}
