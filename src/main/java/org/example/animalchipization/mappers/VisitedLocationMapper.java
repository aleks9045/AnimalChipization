package org.example.animalchipization.mappers;

import org.example.animalchipization.dto.visitedLocation.VLDtoOut;
import org.example.animalchipization.entities.Location;
import org.example.animalchipization.entities.VisitedLocation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @author Aleksey
 */
@Mapper
public interface VisitedLocationMapper {

    @Mapping(target = "id", source = "visitedLocationPointId")

    @Mapping(target = "locationPointId", source = "location")
    VLDtoOut toDto(VisitedLocation entity);

    default Long mapLocationToLong(Location location){
        return location.getLocationId();
    }
}
