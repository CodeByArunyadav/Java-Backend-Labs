package com.employee.model_2.dto;
import com.employee.model_2.annotation.ValidEmployeeName;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO {
    Long id;
    //@NotEmpty(message = "employee can not be null")
    @ValidEmployeeName
    @NotBlank
    String name;
    @Email(message = "Email should be used in proper formate")
    String email;

    @Pattern(
            regexp = "^(IT|HR|FINANCE|SALES|MARKETING|OPERATIONS)$",
            message = "Department must be IT, HR, FINANCE, SALES, MARKETING, or OPERATIONS"
    )
    String department;
    @Positive
    @DecimalMin("10000")
    @DecimalMax("20000")
    BigDecimal salary;
    @PastOrPresent
    Date dateOfJoining;
    boolean active;

}
