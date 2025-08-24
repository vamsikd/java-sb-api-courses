package com.example.webapp.application.course.dto;

import jakarta.validation.constraints.*;

import java.util.Set;

public record CreateCourseDto(
        @NotBlank(message = "name is required") @Size(max = 255, message = "name must be at most 255 chars") String name,
        @Size(max = 1000, message = "description must be at most 1000 chars") String description,
        @NotEmpty(message = "authorIds must contain at least one id") Set<@Positive(message = "authorIds must be positive") Long> authorIds) {
    @Override
    public String toString() {
        return String.format("CreateCourseDto [name=%s, authorIds=%s]", name, authorIds);
    }
}
