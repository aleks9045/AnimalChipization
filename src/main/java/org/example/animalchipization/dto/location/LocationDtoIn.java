package org.example.animalchipization.dto.location;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.example.animalchipization.entity.Location;

/**
 * Represents location data transfer object
 *
 * <p>Used to validate input data in requests via jakarta validation<br><br>
 * All fields must be not null<br>
 * Latitude field must be greater than -90 and less than 90<br>
 * Longitude field must be greater than -180 and less than 180<br>
 *
 * Includes schemas definitions for display in swagger
 *
 * @see Location Location entity
 * @author Aleksey
 */
@AllArgsConstructor
@Setter
@Getter
public class LocationDtoIn {

    @Schema(description = "latitude", example = "41.17235",
            minimum = "-90", maximum = "90", type = "Double")
    @NotNull
    @DecimalMin(value = "-90")
    @DecimalMax(value = "90")
    private Double latitude;

    @Schema(description = "latitude", example = "49.18263",
            minimum = "-180", maximum = "180", type = "Double")
    @NotNull
    @DecimalMin(value = "-180")
    @DecimalMax(value = "180")
    private Double longitude;
}
