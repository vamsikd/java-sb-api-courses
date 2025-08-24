package com.example.webapp.application.course;

import com.example.webapp.domain.entities.Course;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long>, JpaSpecificationExecutor<Course> {
    List<Course> findByAuthors_Id(Long authorId);
}
