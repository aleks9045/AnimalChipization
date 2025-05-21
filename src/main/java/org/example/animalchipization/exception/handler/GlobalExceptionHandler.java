package org.example.animalchipization.exception.handler;

import org.example.animalchipization.exception.dto.ApiError;
import org.example.animalchipization.exception.EntityException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.Instant;

/**
 * Global exception handler
 *
 * @author Aleksey
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * Can't use common interface, because this annotation requires: 'Class<? extends Throwable>'<br>
     * So use abstract class EntityException
     */
    @ExceptionHandler(EntityException.class)
    public ResponseEntity<ApiError> handleEntityException(EntityException ex,
                                                          WebRequest request) {
        HttpStatus httpStatus = ex.getHttpStatus();

        ApiError apiError = this.buildApiError(httpStatus, ex, request);

        return ResponseEntity.status(httpStatus).body(apiError);
    }

    private ApiError buildApiError(HttpStatus httpStatus, EntityException ex, WebRequest request) {
        return new ApiError(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                ex.getMessage(),
                request.getDescription(false).replace("uri=", ""),
                Instant.now()
        );
    }
}
