package org.example.animalchipization.service;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

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
}
