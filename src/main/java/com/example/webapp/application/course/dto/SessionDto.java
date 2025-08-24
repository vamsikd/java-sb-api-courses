package com.example.webapp.application.course.dto;

import java.time.LocalDateTime;

public record SessionDto(
        String title,
        LocalDateTime startsAt,
        LocalDateTime endsAt
) {
}
