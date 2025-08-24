package com.example.webapp.application.student;

import com.example.webapp.application.student.dto.*;
import java.util.List;

public interface StudentService {
    List<StudentDto> getAllStudents();

    StudentDto getStudentById(Integer id);

    StudentDto createStudent(CreateStudentDto createDto);

    StudentDto updateStudent(Integer id, UpdateStudentDto updateDto);

    void deleteStudent(Integer id);
}
