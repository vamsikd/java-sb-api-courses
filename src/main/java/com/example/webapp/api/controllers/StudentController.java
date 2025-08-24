package com.example.webapp.api.controllers;

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
    public ResponseEntity<StudentDto> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(studentService.getStudentById(id));
    }

    // Update
    @PutMapping("{id}")
    public ResponseEntity<StudentDto> update(@PathVariable Integer id, @Valid @RequestBody UpdateStudentDto updateDto) {
        return ResponseEntity.ok(studentService.updateStudent(id, updateDto));
    }

    // Delete
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        studentService.deleteStudent(id);
    }
}
