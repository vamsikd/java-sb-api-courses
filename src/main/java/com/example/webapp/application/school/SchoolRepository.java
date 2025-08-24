package com.example.webapp.application.school;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.webapp.domain.entities.School;

public interface SchoolRepository extends JpaRepository<School, Integer> {

}
