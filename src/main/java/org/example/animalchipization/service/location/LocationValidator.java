package org.example.animalchipization.service.location;

import org.example.animalchipization.entity.Location;
import org.example.animalchipization.service.Validator;

/**
 * @author Aleksey
 */
public interface LocationValidator extends Validator<Location> {

    void checkExistence(Double latitude, Double longitude);
}
