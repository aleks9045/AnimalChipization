package org.example.animalchipization.controllers.animalType;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import org.example.animalchipization.dto.animalType.AnimalTypeDtoIn;
import org.example.animalchipization.dto.animalType.AnimalTypeDtoOut;
import org.example.animalchipization.service.animalType.AnimalTypeService;
import org.springframework.beans.factory.annotation.Autowired;
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
public class AnimalTypeController {
    private final AnimalTypeService animalTypeService;

    @Autowired
    public AnimalTypeController(AnimalTypeService animalTypeService) {
        this.animalTypeService = animalTypeService;
    }

    @GetMapping("/{animalTypeId}")
    @Validated
    public ResponseEntity<AnimalTypeDtoOut> getAnimalTypeById(@PathVariable @Positive @Min(1) Long animalTypeId) {

        AnimalTypeDtoOut animalTypeDtoOut = animalTypeService.getAnimalType(animalTypeId);

        return ResponseEntity.status(HttpStatus.OK).body(animalTypeDtoOut);
    }

    @PostMapping
    @Validated
    public ResponseEntity<AnimalTypeDtoOut> addAnimalType(@Validated @RequestBody AnimalTypeDtoIn animalTypeDtoIn) {

        AnimalTypeDtoOut animalTypeDtoOut = animalTypeService.addAnimalType(animalTypeDtoIn);

        return ResponseEntity.status(HttpStatus.CREATED).body(animalTypeDtoOut);
    }


    @PutMapping("/{animalTypeId}")
    @Validated
    public ResponseEntity<AnimalTypeDtoOut> updateAnimalTypeById(@PathVariable @Positive @Min(1) Long animalTypeId,
                                                             @Validated @RequestBody AnimalTypeDtoIn animalTypeDtoIn) {

        AnimalTypeDtoOut animalTypeDtoOut = animalTypeService.updateAnimalType(animalTypeId, animalTypeDtoIn);

        return ResponseEntity.status(HttpStatus.OK).body(animalTypeDtoOut);
    }

    @DeleteMapping("/{animalTypeId}")
    @Validated
    public void deleteAnimalTypeById(@PathVariable @Positive @Min(1) Long animalTypeId) {
        animalTypeService.deleteAnimalTypeById(animalTypeId);
    }
}
