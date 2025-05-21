package org.example.animalchipization.exception.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;

/**
 * Stores fields with information that describes api error in responses<br>
 *
 * Includes Schema annotations for display in swagger
 * @author Aleksey
 */
@Schema(description = "Standard API error response")
public record ApiError (
        @Schema(example = "404") int status,
        @Schema(example = "Not Found") String error,
        @Schema(example = "Resource not found") String message,
        @Schema(example = "/animals/999") String path,
        @Schema(example = "2025-10-01T12:00:00Z") Instant timestamp
) {}
