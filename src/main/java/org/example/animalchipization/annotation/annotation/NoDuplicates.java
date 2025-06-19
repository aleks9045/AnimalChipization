package org.example.animalchipization.annotation.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.example.animalchipization.annotation.validator.NoDuplicatesValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Custom jakarta validation annotation
 *
 * @author Aleksey
 */
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NoDuplicatesValidator.class)
public @interface NoDuplicates {
    String message() default "must not contain duplicates";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
