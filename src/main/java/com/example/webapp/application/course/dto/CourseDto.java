package com.example.webapp.application.course.dto;

import com.example.webapp.application.common.dto.BaseAuditableDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CourseDto extends BaseAuditableDto {
    private Long id;
    private String name;
    private String description;
    private Set<Long> authorIds;
    private Set<SessionDto> sessions;
}
