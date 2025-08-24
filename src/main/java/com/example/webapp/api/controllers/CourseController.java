package com.example.webapp.api.controllers;

import com.example.webapp.application.common.dto.FilterRequestDto;
import com.example.webapp.application.common.dto.PageDto;
import com.example.webapp.application.course.CourseService;
import com.example.webapp.application.course.dto.CourseCreateDto;
import com.example.webapp.application.course.dto.CourseDto;
import com.example.webapp.application.course.dto.CourseUpdateDto;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {
    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping
    public ResponseEntity<CourseDto> create(@Valid @RequestBody CourseCreateDto dto) {
        CourseDto created = courseService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(courseService.getById(id));
    }

    @GetMapping
    public ResponseEntity<PageDto<CourseDto>> list(
            @RequestParam(required = false) String name,
            @PageableDefault Pageable pageable) {
        Page<CourseDto> page = (name != null && !name.isBlank())
                ? courseService.filter(name, pageable)
                : courseService.list(pageable);
        return ResponseEntity.ok(PageDto.from(page));
    }

    @GetMapping("/all")
    public ResponseEntity<List<CourseDto>> listAll() {
        return ResponseEntity.ok(courseService.listAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<CourseDto> update(@PathVariable Long id, @Valid @RequestBody CourseUpdateDto dto) {
        return ResponseEntity.ok(courseService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        courseService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/search")
    public ResponseEntity<PageDto<CourseDto>> search(@Valid @RequestBody FilterRequestDto request,
            @PageableDefault Pageable pageable) {
        Page<CourseDto> page = courseService.filter(request, pageable);
        return ResponseEntity.ok(PageDto.from(page));
    }
}
