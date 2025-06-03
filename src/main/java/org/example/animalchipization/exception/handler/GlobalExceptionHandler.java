package org.example.animalchipization.exception.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.animalchipization.exception.dto.ApiError;
import org.example.animalchipization.exception.RequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.io.IOException;
import java.time.Instant;

/**
 * Global exception handler
 *
 * @author Aleksey
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RequestException.class)
    public ResponseEntity<ApiError> handleRequestException(RequestException ex,
                                                           HttpServletRequest request) {
        HttpStatus httpStatus = ex.getHttpStatus();

        ApiError apiError = this.buildApiError(httpStatus, ex, request);

        return ResponseEntity.status(httpStatus).body(apiError);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiError> handleAccessDeniedException(SecurityException ex,
                                                            HttpServletRequest request) {

        HttpStatus httpStatus = HttpStatus.FORBIDDEN;

        ApiError apiError = this.buildApiError(httpStatus, ex, request);

        return ResponseEntity.status(httpStatus).body(apiError);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiError> handleSecurityException(SecurityException ex,
                                                            HttpServletRequest request) {

        HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;

        ApiError apiError = this.buildApiError(httpStatus, ex, request);

        return ResponseEntity.status(httpStatus).body(apiError);
    }

    private ApiError buildApiError(HttpStatus httpStatus, Exception ex, HttpServletRequest request) {
        return new ApiError(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                ex.getMessage(),
                request.getRequestURI().replace("uri=", ""),
                Instant.now()
        );
    }

}
