package org.example.animalchipization.dto.location;


import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.example.animalchipization.entity.Location;

/**
 * Represents location data transfer object
 *
 * <p>Used to display output data in responses<br><br>
 * All fields must be not null or blank<br>
 * Id field must be strictly greater than 0<br>
 * Latitude field must be greater than -90 and less than 90<br>
 * Longitude field must be greater than -180 and less than 180<br>
 *
 * @see Location Location entity
 * @author Aleksey
 */
@AllArgsConstructor
@Setter
@Getter
public class LocationDtoOut {

    @NotNull
    @Positive
    @Min(1)
    private Long id;

    @NotNull
    @DecimalMin(value = "-90")
    @DecimalMax(value = "90")
    private Double latitude;

    @NotNull
    @DecimalMin(value = "-180")
    @DecimalMax(value = "180")
    private Double longitude;
}
