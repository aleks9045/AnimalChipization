package org.example.animalchipization.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.example.animalchipization.enums.errors.AnimalError;
import org.example.animalchipization.exception.entities.AnimalException;
import org.example.animalchipization.validation.annotation.NoDuplicates;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Aleksey
 */
public class NoDuplicatesValidator implements ConstraintValidator<NoDuplicates, Collection<?>> {
    @Override
    public boolean isValid(Collection<?> objects, ConstraintValidatorContext context) {

        if (objects == null || objects.isEmpty()) return true;

        if (this.hasDuplicates(objects)){

            context.disableDefaultConstraintViolation();

            throw new AnimalException(AnimalError.ANIMAL_TYPES_DUPLICATES);
        }
        return true;
    }

    private boolean hasDuplicates(Collection<?> objects){
        return objects.size() != new HashSet<>(objects).size();
    }

}
