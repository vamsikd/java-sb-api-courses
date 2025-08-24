package com.example.webapp.application.author.dto;

public record UpdateAuthorDto(
        String firstname,
        String lastname,
        String email) {
    @Override
    public String toString() {
        return String.format("UpdateAuthorDto [firstname=%s, lastname=%s, email=%s]", firstname, lastname, email);
    }
}
