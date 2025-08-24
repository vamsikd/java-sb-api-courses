package com.example.webapp.application.school.dto;

public record SchoolDto(
        Integer id,
        String name,
        String address) {

    @Override
    public String toString() {
        return String.format("SchoolDto [id=%d, name=%s, address=%s]", id, name, address);
    }
}
