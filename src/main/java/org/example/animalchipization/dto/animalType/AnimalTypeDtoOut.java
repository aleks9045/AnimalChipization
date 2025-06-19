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
 * @author Aleksey
 * @see AnimalType Animal type entity
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

    @NotBlank
    @Size(max = 255)
    private String type;
}
