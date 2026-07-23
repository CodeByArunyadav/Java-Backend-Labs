package com.employee.model_2.dto;
import com.employee.model_2.annotation.ValidEmployeeName;
import com.employee.model_2.annotation.ValidateSalary;
import com.employee.model_2.entity.DepartmentEntity;
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
    @NotEmpty(message = "employee can not be null")
    @ValidEmployeeName
    String name;
    @Email(message = "Email should be used in proper formate")
    String email;
    @ValidateSalary
    BigDecimal salary;
    @PastOrPresent
    Date dateOfJoining;
    boolean isActive;
    private DepartmentEntity employeeDepartmentID;

}
