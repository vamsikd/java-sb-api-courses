package com.example.webapp.application.school.dto;

public record UpdateSchoolDto(
        String name,
        String address) {

    @Override
    public String toString() {
        return String.format("UpdateSchoolDto [name=%s, address=%s]", name, address);
    }
}
