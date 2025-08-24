package com.example.webapp.application.student.dto;

public record StudentDto(
        Integer id,
        String firstname,
        String lastname,
        String email,
        Short age,
        Integer schoolId) {

    @Override
    public final String toString() {
        return String.format(
                "StudentDto [firstname=%s, lastname=%s, email=%s, age=%d, schoolId=%s]",
                firstname, lastname, email, age, schoolId);
    }
}
