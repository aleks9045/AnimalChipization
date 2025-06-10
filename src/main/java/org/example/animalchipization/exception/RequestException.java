package org.example.animalchipization.exception;

import lombok.Getter;
import org.example.animalchipization.enums.error.EnumError;
import org.springframework.http.HttpStatus;

/**
 * Exception that throws when business logic violated
 *
 * <p> Stores error message and http status code of error<br>
 * In constructor accepts {@link EnumError}
 *
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
