package com.employee.model_2.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Positive;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER}) // Can be used on fields and method params
@Retention(RetentionPolicy.RUNTIME)                // Available at runtime
@Constraint(validatedBy = SalaryValidator.class)
@Positive
@DecimalMin("10000")
@DecimalMax("20000")// Linked to the logic class
public @interface ValidateSalary {

    String message() default "Invalid salary format. Must be a positive numeric value.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}