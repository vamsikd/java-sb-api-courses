package com.example.webapp.application.author.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateAuthorDto(
        @NotBlank(message = "firstname is required") @Size(max = 100, message = "firstname must be at most 100 chars") String firstname,
        @NotBlank(message = "lastname is required") @Size(max = 100, message = "lastname must be at most 100 chars") String lastname,
        @NotBlank(message = "email is required") @Email(message = "email must be valid") @Size(max = 320, message = "email must be at most 320 chars") String email) {
    @Override
    public String toString() {
        return String.format("CreateAuthorDto [firstname=%s, lastname=%s, email=%s]", firstname, lastname, email);
    }
}
