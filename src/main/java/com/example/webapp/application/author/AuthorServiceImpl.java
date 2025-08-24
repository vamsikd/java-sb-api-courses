package com.example.webapp.application.author;

import com.example.webapp.application.author.dto.*;
import com.example.webapp.domain.entities.Author;
import com.example.webapp.application.common.dto.FilterRequestDto;
import com.example.webapp.application.common.spec.Specifications;
import com.example.webapp.domain.events.AuthorCreatedEvent;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private static final Logger log = LoggerFactory.getLogger(AuthorServiceImpl.class);

    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public List<AuthorDto> listAll() {
        List<Author> authors = authorRepository.findAll();
        return authors.stream().map(authorMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public AuthorDto getById(Long id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Author not found"));
        return authorMapper.toDto(author);
    }

    @Override
    public AuthorDto create(CreateAuthorDto dto) {
        log.info("Attempting to create author with email: {}", dto.email());
        Author author = authorMapper.toEntity(dto);
        Author saved = authorRepository.save(author);
        AuthorDto savedDto = authorMapper.toDto(saved);

        log.info("Author created successfully with ID: {}. Publishing AuthorCreatedEvent.", savedDto.getId());
        eventPublisher.publishEvent(new AuthorCreatedEvent(this, savedDto));
        log.info("AuthorCreatedEvent published for author ID: {}", savedDto.getId());

        return savedDto;
    }

    @Override
    public AuthorDto update(Long id, UpdateAuthorDto dto) {
        Author existing = authorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Author not found"));
        authorMapper.updateEntity(dto, existing);
        Author updated = authorRepository.save(existing);
        return authorMapper.toDto(updated);
    }

    @Override
    public void delete(Long id) {
        Author existing = authorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Author not found"));
        authorRepository.delete(existing);
    }

    @Override
    public Page<AuthorDto> list(Pageable pageable) {
        return authorRepository.findAll(pageable).map(authorMapper::toDto);
    }

    @Override
    public Page<AuthorDto> filter(String firstname, String lastname, String email, Pageable pageable) {
        Specification<Author> spec = (root, query, cb) -> cb.conjunction();

        Specification<Author> firstSpec = Specifications.<Author>containsIgnoreCase("firstname", firstname);
        if (firstSpec != null)
            spec = spec.and(firstSpec);

        Specification<Author> lastSpec = Specifications.<Author>containsIgnoreCase("lastname", lastname);
        if (lastSpec != null)
            spec = spec.and(lastSpec);

        Specification<Author> emailSpec = Specifications.<Author>containsIgnoreCase("email", email);
        if (emailSpec != null)
            spec = spec.and(emailSpec);

        return authorRepository.findAll(spec, pageable).map(authorMapper::toDto);
    }

    @Override
    public Page<AuthorDto> filter(FilterRequestDto request, Pageable pageable) {
        var spec = Specifications.<Author>from(request);
        return authorRepository.findAll(spec, pageable).map(authorMapper::toDto);
    }
}
