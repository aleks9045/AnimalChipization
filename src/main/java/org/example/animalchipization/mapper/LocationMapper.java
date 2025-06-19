package org.example.animalchipization.mapper;

import org.example.animalchipization.dto.location.LocationDtoIn;
import org.example.animalchipization.dto.location.LocationDtoOut;
import org.example.animalchipization.entity.Location;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for {@link Location} entity
 *
 * @author Aleksey
 */
@Mapper
public interface LocationMapper {

    @Mapping(target = "locationId", ignore = true)

    @Mapping(target = "visitedLocations", ignore = true)
    Location toEntity(LocationDtoIn dto);

    @Mapping(target = "id", source = "locationId")
    LocationDtoOut toDto(Location entity);
}
