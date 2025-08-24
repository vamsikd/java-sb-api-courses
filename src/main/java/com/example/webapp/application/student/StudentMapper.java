package com.example.webapp.application.student;

import org.springframework.stereotype.Service;
import com.example.webapp.application.school.SchoolRepository;
import com.example.webapp.application.student.dto.*;
import com.example.webapp.domain.entities.Student;

@Service
public class StudentMapper {

    private final SchoolRepository schoolRepository;

    public StudentMapper(SchoolRepository schoolRepository) {
        this.schoolRepository = schoolRepository;
    }

    public Student toStudent(CreateStudentDto createDto) {
        Student student = new Student();
        student.setFirstname(createDto.firstname());
        student.setLastname(createDto.lastname());
        student.setEmail(createDto.email());
        student.setAge(createDto.age());
        // Don't throw here; assume service validated schoolId. Use a lazy reference to
        // avoid a DB hit.
        if (createDto.schoolId() != null) {
            student.setSchool(schoolRepository.getReferenceById(createDto.schoolId()));
        }
        return student;
    }

    public Student toStudent(UpdateStudentDto updateDto, Student existingStudent) {
        existingStudent.setFirstname(updateDto.firstname());
        existingStudent.setLastname(updateDto.lastname());
        existingStudent.setEmail(updateDto.email());
        if (updateDto.age() != null) {
            existingStudent.setAge(updateDto.age().shortValue());
        } else {
            existingStudent.setAge(null);
        }
        // Only update relation if a schoolId is provided; do not throw in the mapper.
        if (updateDto.schoolId() != null) {
            existingStudent.setSchool(schoolRepository.getReferenceById(updateDto.schoolId()));
        }
        return existingStudent;
    }

    public StudentDto toStudentDto(Student student) {
        Integer schoolId = student.getSchool() != null ? student.getSchool().getId() : null;
        return new StudentDto(
                student.getId(),
                student.getFirstname(),
                student.getLastname(),
                student.getEmail(),
                student.getAge(),
                schoolId);
    }
}
