package org.example.animalchipization.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * @author Aleksey
 */
@Getter
public abstract class EntityException extends RuntimeException {
    private final HttpStatus httpStatus;

    public EntityException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

}
