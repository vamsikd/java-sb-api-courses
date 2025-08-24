package com.example.webapp.application.author;

import com.example.webapp.application.author.dto.AuthorDto;
import com.example.webapp.application.author.dto.CreateAuthorDto;
import com.example.webapp.application.author.dto.UpdateAuthorDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.example.webapp.application.common.dto.FilterRequestDto;

import java.util.List;

public interface AuthorService {
    // Existing simple list (non-paginated) kept for compatibility
    List<AuthorDto> listAll();

    // CRUD
    AuthorDto getById(Long id);

    AuthorDto create(CreateAuthorDto dto);

    AuthorDto update(Long id, UpdateAuthorDto dto);

    void delete(Long id);

    // Pagination
    Page<AuthorDto> list(Pageable pageable);

    // Filter by any property (firstname, lastname, email) with pagination
    Page<AuthorDto> filter(String firstname, String lastname, String email, Pageable pageable);

    // Generic filter from UI (field/op/value with AND/OR)
    Page<AuthorDto> filter(FilterRequestDto request, Pageable pageable);
}
