package com.example.webapp.application.author;

import com.example.webapp.domain.entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long>, JpaSpecificationExecutor<Author> {
    Page<Author> findByFirstnameContainingIgnoreCaseAndLastnameContainingIgnoreCaseAndEmailContainingIgnoreCase(
            String firstname,
            String lastname,
            String email,
            Pageable pageable);
}
