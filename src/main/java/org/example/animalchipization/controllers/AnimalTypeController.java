package org.example.animalchipization.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.example.animalchipization.dto.animalType.AnimalTypeDtoIn;
import org.example.animalchipization.dto.animalType.AnimalTypeDtoOut;
import org.example.animalchipization.service.animalType.AnimalTypeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author Aleksey
 */
@RestController
@RequestMapping("animals/types")
@Tag(name = "animals types")
@RequiredArgsConstructor
public class AnimalTypeController {

    private final AnimalTypeService animalTypeService;


    @GetMapping("/{animalTypeId}")
    @Validated
    public ResponseEntity<AnimalTypeDtoOut> getAnimalTypeById(@PathVariable @Positive @Min(1) Long animalTypeId) {

        var animalTypeDtoOut = animalTypeService.getAnimalType(animalTypeId);

        return ResponseEntity.ok(animalTypeDtoOut);
    }

    @PostMapping
    @Validated
    public ResponseEntity<AnimalTypeDtoOut> addAnimalType(@Validated @RequestBody AnimalTypeDtoIn animalTypeDtoIn) {

        var animalTypeDtoOut = animalTypeService.addAnimalType(animalTypeDtoIn);

        return ResponseEntity.status(HttpStatus.CREATED).body(animalTypeDtoOut);
    }

    @PutMapping("/{animalTypeId}")
    @Validated
    public ResponseEntity<AnimalTypeDtoOut> updateAnimalTypeById(@PathVariable @Positive @Min(1) Long animalTypeId,
                                                             @Validated @RequestBody AnimalTypeDtoIn animalTypeDtoIn) {

        var animalTypeDtoOut = animalTypeService.updateAnimalType(animalTypeId, animalTypeDtoIn);

        return ResponseEntity.ok(animalTypeDtoOut);
    }

    @DeleteMapping("/{animalTypeId}")
    @Validated
    public void deleteAnimalTypeById(@PathVariable @Positive @Min(1) Long animalTypeId) {
        animalTypeService.deleteAnimalTypeById(animalTypeId);
    }
}
