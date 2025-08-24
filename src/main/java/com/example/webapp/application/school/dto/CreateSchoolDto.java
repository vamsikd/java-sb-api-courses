package com.example.webapp.application.school.dto;

public record CreateSchoolDto(
        String name,
        String address) {

    @Override
    public String toString() {
        return String.format("CreateSchoolDto [name=%s, address=%s]", name, address);
    }
}
