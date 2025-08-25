package com.example.webapp.application.student;

import com.example.webapp.domain.entities.StudentProfile;
import org.springframework.stereotype.Service;
import com.example.webapp.application.student.dto.*;
import com.example.webapp.domain.entities.Student;

@Service
public class StudentMapper {

    public Student toEntity(CreateStudentDto createDto) {
        Student student = new Student();
        student.setFirstname(createDto.firstname());
        student.setLastname(createDto.lastname());
        student.setEmail(createDto.email());
        student.setAge(createDto.age());
        if (createDto.profile() != null) {
            StudentProfile profile = this.toEntity(createDto.profile());
            profile.setStudent(student);
            student.setProfile(profile);
        }
        return student;
    }

    public void updateEntity(UpdateStudentDto updateDto, Student existingStudent) {
        existingStudent.setFirstname(updateDto.firstname());
        existingStudent.setLastname(updateDto.lastname());
        existingStudent.setEmail(updateDto.email());
        if (updateDto.age() != null) {
            existingStudent.setAge(updateDto.age().shortValue());
        } else {
            existingStudent.setAge(null);
        }
        if (updateDto.profile() != null) {
            if (existingStudent.getProfile() != null) {
                this.updateEntity(updateDto.profile(), existingStudent.getProfile());
            } else {
                StudentProfile profile = this.toEntity(updateDto.profile());
                profile.setStudent(existingStudent);
                existingStudent.setProfile(profile);
            }

        }
    }

    public StudentDto toDto(Student student) {
        StudentDto dto = new StudentDto();
        dto.setId(student.getId());
        dto.setFirstname(student.getFirstname());
        dto.setLastname(student.getLastname());
        dto.setEmail(student.getEmail());
        dto.setAge(student.getAge());
        dto.setCreatedAt(student.getCreatedAt());
        dto.setUpdatedAt(student.getUpdatedAt());
        dto.setDeleted(student.isDeleted());
        dto.setDeletedAt(student.getDeletedAt());
        if (student.getProfile() != null) {
            dto.setProfile(toDto(student.getProfile()));
        }
        return dto;
    }

    public StudentProfileDto toDto(StudentProfile profile) {
        return new StudentProfileDto(
                profile.getId(),
                profile.getBio(),
                profile.getProfilePictureUrl());
    }

    public StudentProfile toEntity(CreateStudentProfileDto createDto) {
        StudentProfile profile = new StudentProfile();
        profile.setBio(createDto.bio());
        profile.setProfilePictureUrl(createDto.profilePictureUrl());
        return profile;
    }

    public StudentProfile toEntity(UpdateStudentProfileDto createDto) {
        StudentProfile profile = new StudentProfile();
        profile.setBio(createDto.bio());
        profile.setProfilePictureUrl(createDto.profilePictureUrl());
        return profile;
    }

    public void updateEntity(UpdateStudentProfileDto updateDto, StudentProfile existingProfile) {
        existingProfile.setBio(updateDto.bio());
        existingProfile.setProfilePictureUrl(updateDto.profilePictureUrl());
    }
}