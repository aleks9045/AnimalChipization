package org.example.animalchipization.controllers.animal;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.example.animalchipization.dto.animal.*;
import org.example.animalchipization.dto.animal.AnimalDtoOut;
import org.example.animalchipization.dto.animalType.UpdateAnimalTypeDto;
import org.example.animalchipization.dto.location.VisitedLocationSearchCriteria;
import org.example.animalchipization.dto.visitedLocation.UpdateVisitedLocationDto;
import org.example.animalchipization.dto.visitedLocation.VisitedLocationDtoOut;
import org.example.animalchipization.enums.AnimalGender;
import org.example.animalchipization.enums.AnimalLifeStatus;
import org.example.animalchipization.service.visitedLocation.VisitedLocationService;
import org.example.animalchipization.service.animal.AnimalService;
import org.example.animalchipization.service.animalTypeRelation.AnimalTypeRelationsService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

/**
 * @author Aleksey
 */
@RestController
@RequestMapping("animals")
@Tag(name = "animals")
@RequiredArgsConstructor
public class AnimalController {
    private final AnimalService animalService;
    private final AnimalTypeRelationsService animalTypeRelationsService;
    private final VisitedLocationService visitedLocationService;


    @GetMapping("/{animalId}")
    @Validated
    public ResponseEntity<AnimalDtoOut> getAnimalById(@PathVariable @Positive @Min(1) Long animalId) {

        AnimalDtoOut animalDtoOut = animalService.getAnimal(animalId);

        return ResponseEntity.status(HttpStatus.OK).body(animalDtoOut);
    }

    @PostMapping
    @Validated
    public ResponseEntity<AnimalDtoOut> addAnimal(@Validated @RequestBody AnimalDtoIn animalDtoIn) {

        AnimalDtoOut animalDtoOut = animalService.addAnimal(animalDtoIn);

        return ResponseEntity.status(HttpStatus.CREATED).body(animalDtoOut);
    }

    @PutMapping("/{animalId}")
    @Validated
    public ResponseEntity<AnimalDtoOut> updateAnimalById(@PathVariable @Positive @Min(1) Long animalId,
                                                         @Validated @RequestBody AnimalDtoUpdate animalDtoUpdate) {

        AnimalDtoOut animalDtoOut = animalService.updateAnimal(animalId, animalDtoUpdate);
        return ResponseEntity.status(HttpStatus.OK).body(animalDtoOut);
    }

    @DeleteMapping("/{animalId}")
    @Validated
    public void deleteAnimalById(@PathVariable @Positive @Min(1) Long animalId) {
        animalService.deleteAnimalById(animalId);
    }

    @GetMapping("/search")
    public ResponseEntity<List<AnimalDtoOut>> searchAnimals(
            @RequestParam(required = false) Instant startDateTime,
            @RequestParam(required = false) Instant endDateTime,
            @RequestParam(required = false) @Positive @Min(1) Integer chipperId,
            @RequestParam(required = false) @Positive @Min(1) Long chippingLocationId,
            @RequestParam(required = false) AnimalLifeStatus lifeStatus,
            @RequestParam(required = false) AnimalGender gender,
            @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
            @RequestParam(defaultValue = "10") @Positive @Min(1) Integer size) {

        AnimalSearchCriteria animalSearchCriteria = new AnimalSearchCriteria(
                startDateTime,
                endDateTime,
                chipperId,
                chippingLocationId,
                lifeStatus,
                gender);

        List<AnimalDtoOut> animalDtoOutList = animalService.searchAnimals(
                animalSearchCriteria,
                PageRequest.of(from, size, Sort.by("animalId")));

        return ResponseEntity.status(HttpStatus.OK).body(animalDtoOutList);
    }

    @PostMapping("/{animalId}/types/{typeId}")
    @Validated
    public ResponseEntity<AnimalDtoOut> addTypeToAnimal(@PathVariable @Positive @Min(1) Long animalId,
                                                      @PathVariable @Positive @Min(1) Long typeId) {

        AnimalDtoOut animalDtoOut = animalTypeRelationsService.addTypeToAnimal(animalId, typeId);

        return ResponseEntity.status(HttpStatus.CREATED).body(animalDtoOut);
    }

    @PutMapping("/{animalId}/types")
    @Validated
    public ResponseEntity<AnimalDtoOut> replaceTypeInAnimal(@PathVariable @Positive @Min(1) Long animalId,
                                                         @Validated @RequestBody UpdateAnimalTypeDto updateAnimalTypeDto) {

        AnimalDtoOut animalDtoOut = animalTypeRelationsService.replaceTypeInAnimal(animalId, updateAnimalTypeDto);

        return ResponseEntity.status(HttpStatus.OK).body(animalDtoOut);
    }

    @DeleteMapping("/{animalId}/types/{typeId}")
    @Validated
    public ResponseEntity<AnimalDtoOut> removeTypeFromAnimal(@PathVariable @Positive @Min(1) Long animalId,
                                                         @PathVariable @Positive @Min(1) Long typeId) {

        AnimalDtoOut animalDtoOut = animalTypeRelationsService.removeTypeFromAnimal(animalId, typeId);

        return ResponseEntity.status(HttpStatus.OK).body(animalDtoOut);
    }

    @GetMapping("{animalId}/locations")
    @Validated
    public ResponseEntity<List<VisitedLocationDtoOut>> searchAnimalLocations(
            @PathVariable @Positive @Min(1) Long animalId,
            @RequestParam(required = false) Instant startDateTime,
            @RequestParam(required = false) Instant endDateTime,
            @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
            @RequestParam(defaultValue = "10") @Positive @Min(1) Integer size) {

        VisitedLocationSearchCriteria VisitedLocationSearchCriteria = new VisitedLocationSearchCriteria(
                animalId,
                startDateTime,
                endDateTime
        );

        List<VisitedLocationDtoOut> locationDtoOut = visitedLocationService.searchLocations(
                VisitedLocationSearchCriteria,
                PageRequest.of(from, size));

        return ResponseEntity.status(HttpStatus.OK).body(locationDtoOut);
    }

    @PostMapping("/{animalId}/locations/{pointId}")
    @Validated
    public ResponseEntity<VisitedLocationDtoOut> addVisitedLocationToAnimal(
            @PathVariable @Positive @Min(1) Long animalId,
            @PathVariable @Positive @Min(1) Long pointId) {

        VisitedLocationDtoOut VisitedLocationDtoOut = visitedLocationService.addVisitedLocationToAnimal(animalId, pointId);

        return ResponseEntity.status(HttpStatus.CREATED).body(VisitedLocationDtoOut);
    }

    @PutMapping("/{animalId}/locations")
    @Validated
    public ResponseEntity<VisitedLocationDtoOut> replaceVisitedLocationInAnimal(
            @PathVariable @Positive @Min(1) Long animalId,
            @Validated @RequestBody UpdateVisitedLocationDto updateVisitedLocationDto) {

        VisitedLocationDtoOut VisitedLocationDtoOut = visitedLocationService.replaceVisitedLocationInAnimal(animalId, updateVisitedLocationDto);

        return ResponseEntity.status(HttpStatus.OK).body(VisitedLocationDtoOut);
    }

    @DeleteMapping("/{animalId}/locations/{visitedPointId}")
    @Validated
    public ResponseEntity<VisitedLocationDtoOut> removeVisitedLocationFromAnimal(
            @PathVariable @Positive @Min(1) Long animalId,
            @PathVariable @Positive @Min(1) Long visitedPointId) {

        visitedLocationService.removeVisitedLocationFromAnimal(animalId, visitedPointId);

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
