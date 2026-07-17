package com.employee.model_2.dto;
import com.employee.model_2.annotation.ValidEmployeeName;
import com.employee.model_2.annotation.ValidateSalary;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.NumberFormat;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO {
    Long id;
    //@NotEmpty(message = "employee can not be null")
    @NotEmpty(message = "employee can not be null")
    @ValidEmployeeName
    String name;
    @Email(message = "Email should be used in proper formate")
    String email;

    @Pattern(
            regexp = "^(IT|HR|FINANCE|SALES|MARKETING|OPERATIONS)$",
            message = "Department must be IT, HR, FINANCE, SALES, MARKETING, or OPERATIONS"
    )
    String department;
    @ValidateSalary
    BigDecimal salary;
    @PastOrPresent
    Date dateOfJoining;
    boolean isActive;

}
