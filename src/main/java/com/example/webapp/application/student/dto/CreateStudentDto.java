package com.example.webapp.application.student.dto;

import jakarta.validation.constraints.*;

public record CreateStudentDto(
        @NotBlank(message = "firstname is required") @Size(max = 100, message = "firstname must be at most 100 chars") 
        String firstname,
        @NotBlank(message = "lastname is required") @Size(max = 100, message = "lastname must be at most 100 chars") 
        String lastname,
        @NotBlank(message = "email is required") @Email(message = "email must be valid") @Size(max = 200, message = "email must be at most 200 chars") 
        String email,
        @NotNull(message = "age is required") @Min(value = 3, message = "age must be >= 3") @Max(value = 150, message = "age must be <= 150") 
        Short age,
        // Optional: allow null for no school; when present must be positive
        @Positive(message = "schoolId must be positive") 
        Integer schoolId) {

    @Override
    public final String toString() {
        return String.format(
                "CreateStudentDto [firstname=%s, lastname=%s, email=%s, age=%d, schoolId=%s]",
                firstname, lastname, email, age, schoolId);
    }
}
