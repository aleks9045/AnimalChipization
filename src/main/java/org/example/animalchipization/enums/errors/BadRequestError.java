package org.example.animalchipization.enums.errors;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * @author Aleksey
 */
@Getter
public enum BadRequestError implements EnumError {
    ACCOUNT_STILL_LINKED("Account still linked"),

    ANIMAL_ALREADY_DEAD("Animal already dead"),
    ANIMAL_CHIPPING_LOCATION_ALREADY_EXISTS("Animal chipping location already exists"),
    ANIMAL_ALREADY_IN_LOCATION("Animal already in this location"),
    ANIMAL_STILL_LINKED("Animal still linked"),
    ANIMAL_HAS_LEFT_CHIPPING_LOCATION("Animal has left chipping location"),

    ANIMAL_HAS_ONE_TYPE("Animal must contain minimum 1 type"),
    ANIMAL_TYPE_STILL_LINKED("Animal type still linked"),
    LOCATION_STILL_LINKED("Location still linked"),

    VISITED_LOCATION_EQUALS_LOCATION("Location field in visited location equals given location"),
    VISITED_LOCATION_EQUALS_NEXT("Visited location equals next visited location"),
    VISITED_LOCATION_EQUALS_PREVIOUS("Visited location equals previous visited location"),
    PREVIOUS_LOCATION_EQUALS_NEXT("Previous location equals next"),
    VISITED_LOCATION_STILL_LINKED("Visited location still linked");

    private final String message;
    private final HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

    BadRequestError(String message) {
        this.message = message;
    }
}
