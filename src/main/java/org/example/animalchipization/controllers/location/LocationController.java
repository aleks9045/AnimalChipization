package org.example.animalchipization.controllers.location;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import org.example.animalchipization.dto.location.LocationDtoIn;
import org.example.animalchipization.dto.location.LocationDtoOut;
import org.example.animalchipization.service.location.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author Aleksey
 */
@RestController
@RequestMapping("locations")
@Tag(name = "locations")
public class LocationController {
    private final LocationService locationService;

    @Autowired
    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping("/{locationId}")
    @Validated
    public ResponseEntity<LocationDtoOut> getLocationById(@PathVariable @Positive @Min(1) Long locationId) {

        LocationDtoOut locationDtoOut = locationService.getLocation(locationId);

        return ResponseEntity.status(HttpStatus.OK).body(locationDtoOut);
    }

    @PostMapping("/")
    @Validated
    public ResponseEntity<LocationDtoOut> addLocation(@Validated @RequestBody LocationDtoIn locationDtoIn) {

        LocationDtoOut locationDtoOut = locationService.addLocation(locationDtoIn);

        return ResponseEntity.status(HttpStatus.CREATED).body(locationDtoOut);
    }


    @PutMapping("/{locationId}")
    @Validated
    public ResponseEntity<LocationDtoOut> updateLocationById(@PathVariable @Positive @Min(1) Long locationId,
                                                             @Validated @RequestBody LocationDtoIn locationDtoIn) {

        LocationDtoOut locationDtoOut = locationService.updateLocation(locationId, locationDtoIn);

        return ResponseEntity.status(HttpStatus.OK).body(locationDtoOut);
    }

    @DeleteMapping("/{locationId}")
    @Validated
    public void deleteLocationById(@PathVariable @Positive @Min(1) Long locationId) {
        locationService.deleteLocationById(locationId);
    }
}
