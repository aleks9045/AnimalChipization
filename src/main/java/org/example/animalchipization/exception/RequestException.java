package org.example.animalchipization.exception;

import lombok.Getter;
import org.example.animalchipization.enums.errors.EnumError;
import org.springframework.http.HttpStatus;

/**
 * @author Aleksey
 */
@Getter
public class RequestException extends RuntimeException {
    private final HttpStatus httpStatus;

    public RequestException(EnumError enumError) {
        super(enumError.getMessage());
        this.httpStatus = enumError.getHttpStatus();
    }

}
