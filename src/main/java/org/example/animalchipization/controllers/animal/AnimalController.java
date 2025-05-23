package org.example.animalchipization.controllers.animal;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import org.example.animalchipization.dto.animal.*;
import org.example.animalchipization.dto.animal.AnimalDtoOut;
import org.example.animalchipization.enums.AnimalGender;
import org.example.animalchipization.enums.AnimalLifeStatus;
import org.example.animalchipization.service.animal.AnimalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
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
public class AnimalController {
    private final AnimalService animalService;

    @Autowired
    public AnimalController(AnimalService animalService) {
        this.animalService = animalService;
    }

    @GetMapping("/{animalId}")
    @Validated
    public ResponseEntity<AnimalDtoOut> getAnimalById(@PathVariable @Positive @Min(1) Long animalId) {

        AnimalDtoOut animalDtoOut = animalService.getAnimal(animalId);

        return ResponseEntity.status(HttpStatus.OK).body(animalDtoOut);
    }

    @PostMapping("/")
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

        List<AnimalDtoOut> animalDtoOutList = animalService.searchAnimal(
                animalSearchCriteria,
                PageRequest.of(from, size));

        return ResponseEntity.status(HttpStatus.OK).body(animalDtoOutList);
    }
}
