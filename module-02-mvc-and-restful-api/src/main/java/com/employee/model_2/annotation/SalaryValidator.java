package com.employee.model_2.annotation;import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.math.BigDecimal;

public class SalaryValidator implements ConstraintValidator<ValidateSalary, BigDecimal> {

    // Regex allows digits, and optional decimal up to 2 decimal places (e.g., 100, 1500.50)
    private static final String SALARY_REGEX = "^[0-9]+(\\.[0-9]{1,2})?$";

    @Override
    public boolean isValid(
            BigDecimal value,
            ConstraintValidatorContext context) {

        // Let @NotNull handle null separately
        if (value == null) {
            return false;
        }

        boolean greaterThanZero =
                value.compareTo(BigDecimal.ZERO) > 0;

        boolean maximumTwoDecimalPlaces =
                value.scale() <= 2;

        return greaterThanZero && maximumTwoDecimalPlaces;
    }

}


