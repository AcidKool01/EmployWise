package com.example.assignment.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@Document(collection = "employee")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Employee {
    @Id
    String id;

    @NotNull(message = "Employee Name should not be null")
    String EmployeeName;

    @Pattern(regexp = "^\\d{10}$",message = "invalid mobile number entered ")
    String PhoneNumber;

    @Email(message = "invalid email address")
    String Email;
    String reportsTo;
    String profileImage;
}
