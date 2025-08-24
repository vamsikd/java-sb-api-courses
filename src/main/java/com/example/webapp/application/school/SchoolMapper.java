package com.example.webapp.application.school;

import org.springframework.stereotype.Service;

import com.example.webapp.application.school.dto.*;
import com.example.webapp.domain.entities.School;

@Service
public class SchoolMapper {
    public School toSchool(CreateSchoolDto createDto) {
        School school = new School();
        school.setName(createDto.name());
        school.setAddress(createDto.address());
        return school;
    }

    public School toSchool(UpdateSchoolDto updateDto, School existing) {
        existing.setName(updateDto.name());
        existing.setAddress(updateDto.address());
        return existing;
    }

    public SchoolDto toSchoolDto(School school) {
        return new SchoolDto(
                school.getId(),
                school.getName(),
                school.getAddress());
    }
}
