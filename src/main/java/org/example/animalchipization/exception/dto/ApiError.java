package org.example.animalchipization.exception.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;

/**
 * Stores fields with information that describes api error in responses<br>
 * <p>
 * Includes Schema annotations for display in swagger
 *
 * @author Aleksey
 */
@Schema(description = "Standardized error response returned by API when something goes wrong")
public record ApiError(
        @Schema(description = "HTTP status code of the error",
                example = "404")
        int status,

        @Schema(description = "HTTP error reason phrase",
                example = "Not Found")
        String error,

        @Schema(description = "Human-readable error message explaining what went wrong",
                example = "Requested resource was not found")
        String message,

        @Schema(description = "API endpoint path where the error occurred",
                example = "/api/animals/999")
        String path,

        @Schema(description = "Timestamp when the error occurred in ISO-8601 format",
                example = "2025-10-01T12:00:00Z")
        Instant timestamp) {
}
