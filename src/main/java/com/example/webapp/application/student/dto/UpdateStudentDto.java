package com.example.webapp.application.student.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

public record UpdateStudentDto(
        @Size(max = 100, message = "firstname must be at most 100 chars") String firstname,
        @Size(max = 100, message = "lastname must be at most 100 chars") String lastname,
        @Email(message = "email must be valid") @Size(max = 200, message = "email must be at most 200 chars") String email,
        @Min(value = 3, message = "age must be >= 3") @Max(value = 150, message = "age must be <= 150") Short age,
        UpdateStudentProfileDto profile) {

    @Override
    public final String toString() {
        return String.format("UpdateStudentDto [name=%s, email=%s]", firstname, email);
    }
}
