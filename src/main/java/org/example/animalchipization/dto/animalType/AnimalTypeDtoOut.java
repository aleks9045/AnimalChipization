package org.example.animalchipization.dto.animalType;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.example.animalchipization.entities.AnimalType;

/**
 * Represents animal type data transfer object
 *
 * <p>Used to display output data in responses<br>
 * All fields must be not null or blank<br>
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
    private Long id;

    @NotNull
    @NotBlank
    @Size(max = 255)
    private String type;
}
