package org.example.animalchipization.service;

import java.util.List;

/**
 * @author Aleksey
 */
public interface Validator <T> {

    T validateAndGetById(Long id);

    void checkExistence(Long id);
}
