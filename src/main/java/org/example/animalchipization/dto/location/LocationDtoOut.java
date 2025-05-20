package org.example.animalchipization.dto.location;


import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.example.animalchipization.entities.Location;

/**
 * Represents location data transfer object
 *
 * <p>Used to display output data in responses<br>
 * All fields must be not null or blank<br>
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
