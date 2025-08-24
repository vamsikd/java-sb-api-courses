package com.example.webapp.application.school;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.webapp.domain.entities.School;

@Service
public class SchoolLookupService {
    private final SchoolRepository schoolRepository;

    public SchoolLookupService(SchoolRepository schoolRepository) {
        this.schoolRepository = schoolRepository;
    }

    public Optional<School> findById(Integer id) {
        return schoolRepository.findById(id);
    }
}
