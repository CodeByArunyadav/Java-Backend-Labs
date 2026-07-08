package com.employee.model_2.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EmployeeNameValidator implements ConstraintValidator<ValidEmployeeName,String> {

    @Override
    public boolean isValid(String value,
                           ConstraintValidatorContext context) {

        if (value == null || value.trim().isEmpty()) {
            return true; // Let @NotBlank handle null/empty values
        }

        // Reject if the entire value consists only of digits
        return !value.matches("\\d+");
    }
}
