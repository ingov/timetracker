package de.spiegel.timetracker.business.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 *
 * @author veithi
 */
public class CrossCcheckConstraintValidator implements ConstraintValidator<CrossCheck, ValidEntity> {

    @Override
    public void initialize(CrossCheck constraintAnnotation) {
    }

    @Override
    public boolean isValid(ValidEntity entity, ConstraintValidatorContext context) {
        return entity.isValide();
    }
}
