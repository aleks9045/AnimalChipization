package org.example.animalchipization.mapper;


import org.example.animalchipization.dto.animalType.AnimalTypeDtoIn;

import org.example.animalchipization.dto.animalType.AnimalTypeDtoOut;
import org.example.animalchipization.entity.AnimalType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for {@link AnimalType} entity
 *
 * @author Aleksey
 */
@Mapper
public interface AnimalTypeMapper {

    @Mapping(target = "animalTypeId", ignore = true)

    @Mapping(target = "animals", ignore = true)
    AnimalType toEntity(AnimalTypeDtoIn dto);

    @Mapping(target = "id", source = "animalTypeId")
    AnimalTypeDtoOut toDto(AnimalType entity);
}
