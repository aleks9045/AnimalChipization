package org.example.animalchipization.enums.errors;

import org.springframework.http.HttpStatus;

/**
 * @author Aleksey
 */
public interface EnumError {

    String getMessage();
    HttpStatus getHttpStatus();
}
