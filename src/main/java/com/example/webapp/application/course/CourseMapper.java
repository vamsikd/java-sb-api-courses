package com.example.webapp.application.course;

import com.example.webapp.application.course.dto.*;
import com.example.webapp.domain.entities.*;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

@Component
public class CourseMapper {

    public CourseDto toDto(Course c) {
        Set<Long> authorIds = c.getAuthors().stream().map(a -> a.getId()).collect(Collectors.toSet());
        Set<SessionDto> sessions = c.getSessions().stream().map(this::toSessionDto).collect(Collectors.toSet());
        CourseDto dto = new CourseDto();
        dto.setId(c.getId());
        dto.setName(c.getName());
        dto.setDescription(c.getDescription());
        dto.setAuthorIds(authorIds);
        dto.setSessions(sessions);
        dto.setCreatedAt(c.getCreatedAt());
        dto.setUpdatedAt(c.getUpdatedAt());
        dto.setDeleted(c.isDeleted());
        dto.setDeletedAt(c.getDeletedAt());
        return dto;
    }

    public SessionDto toSessionDto(Session session) {
        return new SessionDto(session.getTitle(), session.getStartsAt(), session.getEndsAt());
    }

}
