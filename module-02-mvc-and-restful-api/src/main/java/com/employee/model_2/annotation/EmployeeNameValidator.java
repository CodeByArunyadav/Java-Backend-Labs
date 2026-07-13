package com.employee.model_2.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

// 1. Ensure you specify <ValidEmployeeName, String> right here
public class




















EmployeeNameValidator implements ConstraintValidator<ValidEmployeeName, String> {

    @Override
    public void initialize(ValidEmployeeName constraintAnnotation) {
        // No initialization setup needed
    }

    // 2. Change 'Object value' to 'String value' right here
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // If the name field is empty or null, pass validation
        if (value == null || value.trim().isEmpty()) {
            return true;
        }

        // Now value.matches() will work perfectly without error
        return value.matches("^[^0-9]*$");
    }
}
