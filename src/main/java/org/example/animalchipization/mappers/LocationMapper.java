package org.example.animalchipization.mappers;

import org.example.animalchipization.dto.location.LocationDtoIn;
import org.example.animalchipization.dto.location.LocationDtoOut;
import org.example.animalchipization.models.Location;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @author Aleksey
 */
@Mapper(componentModel = "spring")
public interface LocationMapper {

    @Mapping(target = "locationId", ignore = true)
    Location toModel(LocationDtoIn dto);

    @Mapping(target = "id", source = "locationId")
    LocationDtoOut toDto(Location dto);
}
