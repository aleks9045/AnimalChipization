package org.example.animalchipization.dto.location;


import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.example.animalchipization.entities.Location;

/**
 * Represents location data transfer object
 *
 * @author Aleksey
 * @see Location Location entity
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
