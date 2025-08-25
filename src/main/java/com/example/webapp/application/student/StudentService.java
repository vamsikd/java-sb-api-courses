package com.example.webapp.application.student;

import com.example.webapp.application.common.dto.FilterRequestDto;
import com.example.webapp.application.common.dto.PageDto;
import com.example.webapp.application.student.dto.*;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface StudentService {
    List<StudentDto> getAllStudents();

    StudentDto getStudentById(Long id);

    StudentDto createStudent(CreateStudentDto createDto);

    StudentDto updateStudent(Long id, UpdateStudentDto updateDto);

    void deleteStudent(Long id);

    PageDto<StudentDto> list(Pageable pageable);

    PageDto<StudentDto> filter(String firstname, String lastname, String email, Pageable pageable);

    PageDto<StudentDto> filter(FilterRequestDto request, Pageable pageable);

    StudentProfileDto createStudentProfile(Long studentId, CreateStudentProfileDto createDto);

    StudentProfileDto updateStudentProfile(Long studentId, UpdateStudentProfileDto updateDto);
}
