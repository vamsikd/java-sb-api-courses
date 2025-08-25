package com.example.webapp.application.student;

import com.example.webapp.application.common.dto.FilterRequestDto;
import com.example.webapp.application.common.dto.PageDto;
import com.example.webapp.application.common.spec.Specifications;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.example.webapp.application.student.dto.*;
import com.example.webapp.domain.entities.Student;
import com.example.webapp.domain.entities.StudentProfile;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;

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
    public StudentDto getStudentById(Long id) {
        // find by id
        Student student = studentRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Student not found"));

        // reverse map
        return studentMapper.toDto(student);
    }

    @Override
    public List<StudentDto> getAllStudents() {
        return studentRepo.findAll()
                .stream()
                .map(studentMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public StudentDto createStudent(CreateStudentDto createDto) {
        // Validate input

        // map dto
        Student student = studentMapper.toEntity(createDto);

        // save entity
        Student savedStudent = studentRepo.save(student);

        // reverse map
        return studentMapper.toDto(savedStudent);
    }

    @Override
    public StudentDto updateStudent(Long id, UpdateStudentDto updateDto) {
        // find by id
        Student existingStudent = studentRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Student not found"));

        // map dto to entity
        studentMapper.updateEntity(updateDto, existingStudent);

        // save updated entity
        Student updatedStudent = studentRepo.save(existingStudent);

        // reverse map
        return studentMapper.toDto(updatedStudent);
    }

    @Override
    public void deleteStudent(Long id) {
        // find by id
        Student student = studentRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Student not found"));

        // delete
        studentRepo.delete(student);
    }

    @Override
    public PageDto<StudentDto> list(Pageable pageable) {
        return PageDto.from(studentRepo.findAll(pageable).map(studentMapper::toDto));
    }

    @Override
    public PageDto<StudentDto> filter(String firstname, String lastname, String email, Pageable pageable) {
        Specification<Student> spec = (root, query, cb) -> cb.conjunction();

        Specification<Student> firstSpec = Specifications.<Student>containsIgnoreCase("firstname", firstname);
        if (firstSpec != null)
            spec = spec.and(firstSpec);

        Specification<Student> lastSpec = Specifications.<Student>containsIgnoreCase("lastname", lastname);
        if (lastSpec != null)
            spec = spec.and(lastSpec);

        Specification<Student> emailSpec = Specifications.<Student>containsIgnoreCase("email", email);
        if (emailSpec != null)
            spec = spec.and(emailSpec);

        return PageDto.from(studentRepo.findAll(spec, pageable).map(studentMapper::toDto));
    }

    @Override
    public PageDto<StudentDto> filter(FilterRequestDto request, Pageable pageable) {
        var spec = Specifications.<Student>from(request);
        return PageDto.from(studentRepo.findAll(spec, pageable).map(studentMapper::toDto));
    }

    @Override
    public StudentProfileDto createStudentProfile(Long studentId, CreateStudentProfileDto createDto) {
        Student student = studentRepo.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("Student not found"));

        if (student.getProfile() != null) {
            throw new IllegalStateException("Student already has a profile");
        }

        StudentProfile profile = studentMapper.toEntity(createDto);
        student.setProfile(profile);
        profile.setStudent(student);

        studentRepo.save(student);

        return studentMapper.toDto(profile);
    }

    @Override
    public StudentProfileDto updateStudentProfile(Long studentId, UpdateStudentProfileDto updateDto) {
        Student student = studentRepo.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("Student not found"));

        StudentProfile profile = student.getProfile();
        if (profile == null) {
            profile = new StudentProfile();
            student.setProfile(profile);
            profile.setStudent(student);
        }

        studentMapper.updateEntity(updateDto, profile);
        studentRepo.save(student);

        return studentMapper.toDto(profile);
    }

}
