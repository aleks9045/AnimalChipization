package org.example.animalchipization.mappers;

import org.example.animalchipization.dto.animal.AnimalDtoIn;
import org.example.animalchipization.dto.animal.AnimalDtoOut;
import org.example.animalchipization.entities.Animal;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for {@link Animal} entity
 * <p>Converting one object to another by transfer first object fields to second object
 * using setters and getters
 *
 * @author Aleksey
 */
@Mapper(componentModel = "spring")
public interface AnimalMapper {

    @Mapping(target = "animalId", ignore = true)
    Animal toEntity(AnimalDtoIn dto);

    @Mapping(target = "id", source = "animalId")
    AnimalDtoOut toDto(Animal dto);
}
