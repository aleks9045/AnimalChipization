package org.example.animalchipization.mappers;

import org.example.animalchipization.dto.animal.AnimalDtoIn;
import org.example.animalchipization.dto.animal.AnimalDtoOut;
import org.example.animalchipization.models.Animal;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @author Aleksey
 */
@Mapper(componentModel = "spring")
public interface AnimalMapper {

    @Mapping(target = "animalId", ignore = true)
    Animal toModel(AnimalDtoIn dto);

    @Mapping(target = "id", source = "animalId")
    AnimalDtoOut toDto(Animal dto);
}
