package com.example.webapp.application.author;

import com.example.webapp.application.author.dto.AuthorDto;
import com.example.webapp.application.author.dto.CreateAuthorDto;
import com.example.webapp.application.author.dto.UpdateAuthorDto;
import com.example.webapp.domain.entities.Author;
import org.springframework.stereotype.Component;

@Component
public class AuthorMapper {
    public AuthorDto toDto(Author a) {
        AuthorDto dto = new AuthorDto();
        dto.setId(a.getId());
        dto.setFirstname(a.getFirstname());
        dto.setLastname(a.getLastname());
        dto.setEmail(a.getEmail());
        dto.setBio(a.getBio());
        dto.setCreatedAt(a.getCreatedAt());
        dto.setUpdatedAt(a.getUpdatedAt());
        dto.setDeleted(a.isDeleted());
        dto.setDeletedAt(a.getDeletedAt());
        return dto;
    }

    public Author toEntity(CreateAuthorDto dto) {
        Author a = new Author();
        a.setFirstname(dto.firstname());
        a.setLastname(dto.lastname());
        a.setEmail(dto.email());
        return a;
    }

    public void updateEntity(UpdateAuthorDto dto, Author target) {
        if (dto.firstname() != null)
            target.setFirstname(dto.firstname());
        if (dto.lastname() != null)
            target.setLastname(dto.lastname());
        if (dto.email() != null)
            target.setEmail(dto.email());
    }
}
