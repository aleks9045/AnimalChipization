package org.example.animalchipization.exception.entities;

import org.example.animalchipization.enums.errors.VLError;
import org.example.animalchipization.exception.EntityException;

/**
 * @author Aleksey
 */
public class VLException extends EntityException {

    public VLException(VLError VLError) {
        super(VLError.getMessage(), VLError.getHttpStatus());
    }
}
