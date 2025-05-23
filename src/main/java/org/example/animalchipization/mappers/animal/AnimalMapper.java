package org.example.animalchipization.mappers.animal;

import org.example.animalchipization.dto.animal.AnimalDtoIn;
import org.example.animalchipization.dto.animal.AnimalDtoOut;
import org.example.animalchipization.dto.animal.AnimalDtoUpdate;
import org.example.animalchipization.entities.Account;
import org.example.animalchipization.entities.Animal;
import org.example.animalchipization.entities.AnimalType;
import org.example.animalchipization.entities.Location;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.HashSet;
import java.util.Set;

/**
 * Mapper for {@link Animal} entity
 * <p>Converting one object to another by transfer first object fields to second object
 * using setters and getters
 *
 * @author Aleksey
 */
@Mapper(componentModel = "spring", uses = AnimalMapperHelper.class)
public interface AnimalMapper {

    @Mapping(target = "animalId", ignore = true)
    @Mapping(target = "animalTypes", ignore = true)
    Animal toEntity(AnimalDtoIn dto);

    @Mapping(target = "animalId", ignore = true)
    Animal toEntity(AnimalDtoUpdate dto);

    @Mapping(target = "id", source = "animalId")
    AnimalDtoOut toDto(Animal dto);

    void updateEntity(@MappingTarget Animal animal, AnimalDtoUpdate animalDtoUpdate);

}
