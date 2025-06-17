package org.example.animalchipization.dto.animalType;


import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.example.animalchipization.entity.AnimalType;

/**
 * Represents animal type data transfer object
 *
 * <p>Used to display output data in responses<br><br>
 * All fields must be not null or blank<br>
 * Id field must be strictly greater than 0
 *
 * @see AnimalType Animal type entity
 * @author Aleksey
 */
@AllArgsConstructor
@Setter
@Getter
@ToString
public class AnimalTypeDtoOut {

    @NotNull
    @Positive
    @Min(1)
    private Long id;

    @NotNull
    @NotBlank
    @Size(max = 255)
    private String type;
}
