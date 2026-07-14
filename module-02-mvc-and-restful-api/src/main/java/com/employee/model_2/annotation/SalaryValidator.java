package com.employee.model_2.annotation;import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.math.BigDecimal;

public class SalaryValidator implements ConstraintValidator<ValidateSalary, String> {

    // Regex allows digits, and optional decimal up to 2 decimal places (e.g., 100, 1500.50)
    private static final String SALARY_REGEX = "^[0-9]+(\\.[0-9]{1,2})?$";

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // Allow null if you want @NotNull to handle missing fields separately
        if (value == null || value.trim().isEmpty()) {
            return true;
        }

        // 1. Check if string contains invalid characters like "102fg3"
        if (!value.matches(SALARY_REGEX)) {
            return false;
        }

        // 2. Safely parse to BigDecimal and check numeric boundaries
        try {
            BigDecimal salaryNum = new BigDecimal(value);
            return salaryNum.compareTo(BigDecimal.ZERO) > 0; // Must be greater than 0
        } catch (NumberFormatException e) {
            return false;
        }
    }
}


