package com.example.webapp.application.course;

import com.example.webapp.application.common.dto.FilterRequestDto;
import com.example.webapp.application.common.spec.Specifications;
import com.example.webapp.application.course.dto.*;
import com.example.webapp.domain.entities.*;
import com.example.webapp.application.author.AuthorRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;
    private final AuthorRepository authorRepository;

    @Override
    public CourseDto getById(Long id) {
        return courseRepository.findById(id).map(courseMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Course not found with id: " + id));
    }

    @Override
    public Page<CourseDto> list(Pageable pageable) {
        return courseRepository.findAll(pageable).map(courseMapper::toDto);
    }

    @Override
    public List<CourseDto> listAll() {
        return courseRepository.findAll().stream().map(courseMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public CourseDto create(CourseCreateDto dto) {

        var authors = authorRepository.findAllById(dto.authorIds()).stream().collect(Collectors.toSet());
        Course course = Course.create(dto.name(), authors);
        course.setDescription(dto.description());

        dto.sessions().forEach(s -> {
            Session session = Session.schedule(s.title(), s.startsAt(), s.endsAt());
            course.addSession(session);
        });

        return courseMapper.toDto(courseRepository.save(course));
    }

    @Override
    public CourseDto update(Long id, CourseUpdateDto dto) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Course not found with id: " + id));

        var authors = authorRepository.findAllById(dto.authorIds()).stream().collect(Collectors.toSet());
        course.getAuthors().clear();
        authors.forEach(course::addAuthor);

        course.rename(dto.name());
        course.setDescription(dto.description());

        // Clear existing sessions and add new ones
        course.getSessions().clear();
        dto.sessions().forEach(s -> {
            Session session = Session.schedule(s.title(), s.startsAt(), s.endsAt());
            course.addSession(session);
        });

        return courseMapper.toDto(courseRepository.save(course));
    }

    @Override
    public void delete(Long id) {
        courseRepository.deleteById(id);
    }

    @Override
    public Page<CourseDto> filter(String name, Pageable pageable) {
        Specification<Course> spec = (root, query, cb) -> cb.like(cb.lower(root.get("name")),
                "%" + name.toLowerCase() + "%");
        return courseRepository.findAll(spec, pageable).map(courseMapper::toDto);
    }

    @Override
    public Page<CourseDto> filter(FilterRequestDto request, Pageable pageable) {
        Specification<Course> spec = Specifications.from(request);
        return courseRepository.findAll(spec, pageable).map(courseMapper::toDto);
    }

    @Override
    public List<CourseDto> listByAuthorId(Long authorId) {
        List<Course> courses = courseRepository.findByAuthors_Id(authorId);
        return courses.stream().map(courseMapper::toDto).collect(Collectors.toList());
    }
}
