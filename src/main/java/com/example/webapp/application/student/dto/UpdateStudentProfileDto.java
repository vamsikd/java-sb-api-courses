package com.example.webapp.application.student.dto;

import jakarta.validation.constraints.Size;

public record UpdateStudentProfileDto(
        @Size(max = 1000, message = "bio must be at most 1000 chars")
        String bio,
        @Size(max = 255, message = "profilePictureUrl must be at most 255 chars")
        String profilePictureUrl) {
}
