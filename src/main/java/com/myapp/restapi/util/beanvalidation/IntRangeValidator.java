package com.myapp.restapi.util.beanvalidation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class IntRangeValidator implements ConstraintValidator<IntRange, Integer> {

    
	private int min;
	private int max;
	@Override
    public void initialize(IntRange constraintAnnotation) {
        // Initialization code, if needed
		min = constraintAnnotation.min();
		max = constraintAnnotation.max();
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        if (value == null) {
            return false; // or true if you want to allow empty string
        }
        try {
           // int intValue = Integer.parseInt(value);
            return value >= min && value <= max;
        } catch (NumberFormatException e) {
            return false; // Not a valid integer
        }
    }
}

