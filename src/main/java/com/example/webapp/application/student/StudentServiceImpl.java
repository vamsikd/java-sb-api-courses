package com.example.webapp.application.student;

import org.springframework.stereotype.Service;

import com.example.webapp.application.student.dto.*;
import com.example.webapp.domain.entities.Student;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepo;
    private final StudentMapper studentMapper;

    public StudentServiceImpl(StudentRepository studentRepository, StudentMapper studentMapper) {
        this.studentRepo = studentRepository;
        this.studentMapper = studentMapper;
    }

    @Override
    public StudentDto getStudentById(Integer id) {
        // find by id
        Student student = studentRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Student not found"));

        // reverse map
        return studentMapper.toStudentDto(student);
    }

    @Override
    public List<StudentDto> getAllStudents() {
        return studentRepo.findAll()
                .stream()
                .map(studentMapper::toStudentDto)
                .collect(Collectors.toList());
    }

    @Override
    public StudentDto createStudent(CreateStudentDto createDto) {
        // Validate input

        // map dto
        Student student = studentMapper.toStudent(createDto);

        // save entity
        Student savedStudent = studentRepo.save(student);

        // reverse map
        return studentMapper.toStudentDto(savedStudent);
    }

    @Override
    public StudentDto updateStudent(Integer id, UpdateStudentDto updateDto) {
        // find by id
        Student existingStudent = studentRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Student not found"));

        // map dto to entity
        studentMapper.toStudent(updateDto, existingStudent);

        // save updated entity
        Student updatedStudent = studentRepo.save(existingStudent);

        // reverse map
        return studentMapper.toStudentDto(updatedStudent);
    }

    @Override
    public void deleteStudent(Integer id) {
        // find by id
        Student student = studentRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Student not found"));

        // delete
        studentRepo.delete(student);
    }

}
