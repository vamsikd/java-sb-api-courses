package com.example.webapp.api.controllers;

import com.example.webapp.application.common.dto.FilterRequestDto;
import com.example.webapp.application.common.dto.PageDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.webapp.application.student.StudentService;
import com.example.webapp.application.student.dto.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    // Create
    @PostMapping
    public ResponseEntity<StudentDto> create(@Valid @RequestBody CreateStudentDto createDto) {
        StudentDto created = studentService.createStudent(createDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // Read all
    @GetMapping
    public ResponseEntity<List<StudentDto>> getAll() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    // Read by Id
    @GetMapping("{id}")
    public ResponseEntity<StudentDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.getStudentById(id));
    }

    // Update
    @PutMapping("{id}")
    public ResponseEntity<StudentDto> update(@PathVariable Long id, @Valid @RequestBody UpdateStudentDto updateDto) {
        return ResponseEntity.ok(studentService.updateStudent(id, updateDto));
    }

    // Delete
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        studentService.deleteStudent(id);
    }

    @GetMapping("/paged")
    public ResponseEntity<PageDto<StudentDto>> list(Pageable pageable) {
        return ResponseEntity.ok(studentService.list(pageable));
    }

    @GetMapping("/filter")
    public ResponseEntity<PageDto<StudentDto>> filter(
            @RequestParam(required = false) String firstname,
            @RequestParam(required = false) String lastname,
            @RequestParam(required = false) String email,
            Pageable pageable) {
        return ResponseEntity.ok(studentService.filter(firstname, lastname, email, pageable));
    }

    @PostMapping("/filter")
    public ResponseEntity<PageDto<StudentDto>> filter(@RequestBody FilterRequestDto request, Pageable pageable) {
        return ResponseEntity.ok(studentService.filter(request, pageable));
    }

    @PostMapping("{studentId}/profile")
    public ResponseEntity<StudentProfileDto> createProfile(@PathVariable Long studentId, @Valid @RequestBody CreateStudentProfileDto createDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(studentService.createStudentProfile(studentId, createDto));
    }

    @PutMapping("{studentId}/profile")
    public ResponseEntity<StudentProfileDto> updateProfile(@PathVariable Long studentId, @Valid @RequestBody UpdateStudentProfileDto updateDto) {
        return ResponseEntity.ok(studentService.updateStudentProfile(studentId, updateDto));
    }
}
