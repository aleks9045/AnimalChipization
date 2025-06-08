package org.example.animalchipization.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.example.animalchipization.enums.errors.ConflictError;
import org.example.animalchipization.exception.RequestException;
import org.example.animalchipization.validation.annotation.NoDuplicates;

import java.util.Collection;
import java.util.HashSet;

/**
 * Validator for {@link NoDuplicates} annotation
 *
 * <p>Check that collection has no duplicate values
 *
 * @author Aleksey
 */
public class NoDuplicatesValidator implements ConstraintValidator<NoDuplicates, Collection<?>> {

    @Override
    public boolean isValid(Collection<?> objects, ConstraintValidatorContext context) {

        if (objects == null || objects.isEmpty()) return true;

        if (this.hasDuplicates(objects)){

            context.disableDefaultConstraintViolation();

            throw new RequestException(ConflictError.ANIMAL_TYPES_DUPLICATES);
        }
        return true;
    }

    private boolean hasDuplicates(Collection<?> objects){
        return objects.size() != new HashSet<>(objects).size();
    }

}
