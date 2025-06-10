package org.example.animalchipization.mapper;

import org.example.animalchipization.dto.visitedLocation.VisitedLocationDtoOut;
import org.example.animalchipization.entity.Location;
import org.example.animalchipization.entity.VisitedLocation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @author Aleksey
 */
@Mapper
public interface VisitedLocationMapper {

    @Mapping(target = "id", source = "visitedLocationPointId")

    @Mapping(target = "locationPointId", source = "location")
    VisitedLocationDtoOut toDto(VisitedLocation entity);

    default Long mapLocationToLong(Location location){
        return location.getLocationId();
    }
}
