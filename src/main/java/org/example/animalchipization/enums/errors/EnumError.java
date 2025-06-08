package org.example.animalchipization.enums.errors;

import org.springframework.http.HttpStatus;

/**
 * Interface that unite all error enumerations
 * @author Aleksey
 */
public interface EnumError {

    String getMessage();
    HttpStatus getHttpStatus();
}
