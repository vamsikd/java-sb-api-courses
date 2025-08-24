package com.example.webapp.application.student.dto;

public record UpdateStudentDto(
        String firstname,
        String lastname,
        String email,
        Integer age,
        Integer schoolId) {

    @Override
    public final String toString() {
        return String.format("UpdateStudentDto [name=%s, email=%s, schoolId=%s]", firstname, email, schoolId);
    }
}
