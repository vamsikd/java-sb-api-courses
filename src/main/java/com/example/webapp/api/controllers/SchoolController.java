package com.example.webapp.api.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.webapp.application.school.SchoolService;
import com.example.webapp.application.school.dto.*;
import java.util.List;

@RestController
@RequestMapping("/api/schools")
public class SchoolController {

    private final SchoolService schoolService;

    public SchoolController(SchoolService schoolService) {
        this.schoolService = schoolService;
    }

    // Create
    @PostMapping
    public ResponseEntity<SchoolDto> create(@RequestBody CreateSchoolDto createDto) {
        SchoolDto created = schoolService.createSchool(createDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // Read all
    @GetMapping
    public ResponseEntity<List<SchoolDto>> getAll() {
        return ResponseEntity.ok(schoolService.getAllSchools());
    }

    // Read by Id
    @GetMapping("{id}")
    public ResponseEntity<SchoolDto> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(schoolService.getSchoolById(id));
    }

    // Update
    @PutMapping("{id}")
    public ResponseEntity<SchoolDto> update(@PathVariable Integer id, @RequestBody UpdateSchoolDto updateDto) {
        return ResponseEntity.ok(schoolService.updateSchool(id, updateDto));
    }

    // Delete
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        schoolService.deleteSchool(id);
    }
}
