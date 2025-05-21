package org.example.animalchipization.mappers;


import org.example.animalchipization.dto.animalType.AnimalTypeDtoIn;

import org.example.animalchipization.dto.animalType.AnimalTypeDtoOut;
import org.example.animalchipization.entities.AnimalType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for {@link AnimalType} entity
 * <p>Converting one object to another by transfer first object fields to second object
 * using setters and getters
 *
 * @author Aleksey
 */
@Mapper(componentModel = "spring")
public interface AnimalTypeMapper {

    @Mapping(target = "animalTypeId", ignore = true)
    AnimalType toEntity(AnimalTypeDtoIn dto);

    @Mapping(target = "id", source = "animalTypeId")
    AnimalTypeDtoOut toDto(AnimalType model);
}
