package com.example.webapp.application.course.dto;

import java.util.Set;

public record UpdateCourseDto(
        String name,
        String description,
        Set<Long> authorIds) {
    @Override
    public String toString() {
        return String.format("UpdateCourseDto [name=%s, authorIds=%s]", name, authorIds);
    }
}
