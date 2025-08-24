package com.example.webapp.application.course;

import com.example.webapp.application.common.dto.FilterRequestDto;
import com.example.webapp.application.course.dto.CourseCreateDto;
import com.example.webapp.application.course.dto.CourseDto;
import com.example.webapp.application.course.dto.CourseUpdateDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CourseService {

    CourseDto create(CourseCreateDto dto);

    CourseDto getById(Long id);

    Page<CourseDto> list(Pageable pageable);

    List<CourseDto> listAll();

    CourseDto update(Long id, CourseUpdateDto dto);

    void delete(Long id);

    Page<CourseDto> filter(String name, Pageable pageable);

    Page<CourseDto> filter(FilterRequestDto request, Pageable pageable);

    List<CourseDto> listByAuthorId(Long authorId);
}
