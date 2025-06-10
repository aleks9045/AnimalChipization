package org.example.animalchipization.mapper.animal;

import org.example.animalchipization.dto.animal.AnimalDtoIn;
import org.example.animalchipization.dto.animal.AnimalDtoOut;
import org.example.animalchipization.dto.animal.AnimalDtoUpdate;
import org.example.animalchipization.entity.Animal;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * Mapper for {@link Animal} entity
 * <p>Converting one object to another by transfer first object fields to second object
 * using setters and getters
 *
 * @author Aleksey
 */
@Mapper(uses = AnimalMapperHelper.class)
public interface AnimalMapper {

    @Mapping(target = "animalId", ignore = true)

    @Mapping(target = "animalTypes", ignore = true)
    @Mapping(target = "chippingDateTime", ignore = true)
    @Mapping(target = "visitedLocations", ignore = true)
    @Mapping(target = "lifeStatus", ignore = true)
    @Mapping(target = "deathDateTime", ignore = true)
    Animal toEntity(AnimalDtoIn dto);

    @Mapping(target = "animalId", ignore = true)

    @Mapping(target = "animalTypes", ignore = true)
    @Mapping(target = "chippingDateTime", ignore = true)
    @Mapping(target = "visitedLocations", ignore = true)
    @Mapping(target = "deathDateTime", ignore = true)
    Animal toEntity(AnimalDtoUpdate dto);

    @Mapping(target = "id", source = "animalId")
    AnimalDtoOut toDto(Animal entity);

    @Mapping(target = "animalId", ignore = true)

    @Mapping(target = "animalTypes", ignore = true)
    @Mapping(target = "chippingDateTime", ignore = true)
    @Mapping(target = "visitedLocations", ignore = true)
    @Mapping(target = "deathDateTime", ignore = true)
    void updateEntity(@MappingTarget Animal animal, AnimalDtoUpdate animalDtoUpdate);

}
