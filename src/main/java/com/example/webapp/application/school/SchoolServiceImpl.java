package com.example.webapp.application.school;

import org.springframework.stereotype.Service;

import com.example.webapp.application.school.dto.*;
import com.example.webapp.domain.entities.School;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SchoolServiceImpl implements SchoolService {

    private final SchoolRepository schoolRepo;
    private final SchoolMapper schoolMapper;

    public SchoolServiceImpl(SchoolRepository schoolRepository, SchoolMapper schoolMapper) {
        this.schoolRepo = schoolRepository;
        this.schoolMapper = schoolMapper;
    }

    @Override
    public SchoolDto getSchoolById(Integer id) {
        School school = schoolRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("School not found"));
        return schoolMapper.toSchoolDto(school);
    }

    @Override
    public List<SchoolDto> getAllSchools() {
        return schoolRepo.findAll()
                .stream()
                .map(schoolMapper::toSchoolDto)
                .collect(Collectors.toList());
    }

    @Override
    public SchoolDto createSchool(CreateSchoolDto createDto) {
        School school = schoolMapper.toSchool(createDto);
        School saved = schoolRepo.save(school);
        return schoolMapper.toSchoolDto(saved);
    }

    @Override
    public SchoolDto updateSchool(Integer id, UpdateSchoolDto updateDto) {
        School existing = schoolRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("School not found"));
        schoolMapper.toSchool(updateDto, existing);
        School updated = schoolRepo.save(existing);
        return schoolMapper.toSchoolDto(updated);
    }

    @Override
    public void deleteSchool(Integer id) {
        School school = schoolRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("School not found"));
        schoolRepo.delete(school);
    }
}
