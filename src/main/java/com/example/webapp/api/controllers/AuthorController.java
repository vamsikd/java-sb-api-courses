package com.example.webapp.api.controllers;

import com.example.webapp.application.author.AuthorService;
import com.example.webapp.application.author.dto.*;
import com.example.webapp.application.common.dto.FilterRequestDto;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import com.example.webapp.application.common.dto.PageDto;

@RestController
@RequestMapping("/api/authors")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    // Create
    @PostMapping
    public ResponseEntity<AuthorDto> create(@Valid @RequestBody CreateAuthorDto dto) {
        AuthorDto created = authorService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // Read by id
    @GetMapping("/{id}")
    public ResponseEntity<AuthorDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(authorService.getById(id));
    }

    // List (paged) with optional simple filters
    @GetMapping
    public ResponseEntity<PageDto<AuthorDto>> list(
            @RequestParam(required = false) String firstname,
            @RequestParam(required = false) String lastname,
            @RequestParam(required = false) String email,
            @PageableDefault Pageable pageable) {
        Page<AuthorDto> page = ((firstname != null && !firstname.isBlank()) ||
                (lastname != null && !lastname.isBlank()) ||
                (email != null && !email.isBlank()))
                        ? authorService.filter(firstname, lastname, email, pageable)
                        : authorService.list(pageable);
        return ResponseEntity.ok(PageDto.from(page));
    }

    // List all (non-paged) — optional convenience
    @GetMapping("/all")
    public ResponseEntity<List<AuthorDto>> listAll() {
        return ResponseEntity.ok(authorService.listAll());
    }

    // Update
    @PutMapping("/{id}")
    public ResponseEntity<AuthorDto> update(@PathVariable Long id, @Valid @RequestBody UpdateAuthorDto dto) {
        return ResponseEntity.ok(authorService.update(id, dto));
    }

    // Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        authorService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // Generic filter via POST body
    @PostMapping("/search")
    public ResponseEntity<PageDto<AuthorDto>> search(@Valid @RequestBody FilterRequestDto request,
            @PageableDefault Pageable pageable) {
        Page<AuthorDto> page = authorService.filter(request, pageable);
        return ResponseEntity.ok(PageDto.from(page));
    }
}