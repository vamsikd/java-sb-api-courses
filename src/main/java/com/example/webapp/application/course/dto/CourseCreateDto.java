package com.example.webapp.application.course.dto;

import com.example.webapp.application.common.validation.NotEmptySet;
import jakarta.validation.constraints.NotBlank;

import java.util.Set;

public record CourseCreateDto(
                @NotBlank String name,
                String description,
                @NotEmptySet Set<Long> authorIds,
                @NotEmptySet Set<SessionDto> sessions) {
}
