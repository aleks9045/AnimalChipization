package org.example.animalchipization.mappers;

import org.example.animalchipization.dto.visitedLocation.VisitedLocationDtoOut;
import org.example.animalchipization.entities.Location;
import org.example.animalchipization.entities.VisitedLocation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @author Aleksey
 */
@Mapper(componentModel = "spring")
public interface VisitedLocationMapper {

    @Mapping(target = "id", source = "visitedLocationPointId")
    @Mapping(target = "locationPointId", source = "location")
    VisitedLocationDtoOut toDto(VisitedLocation entity);

    default Long mapLocationToLong(Location location){
        return location.getLocationId();
    }
}
