package com.example.webapp.domain.entities;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "courses", indexes = {
        @Index(name = "ix_courses_is_deleted", columnList = "is_deleted")
})
@SQLRestriction("is_deleted = false")
@SQLDelete(sql = "UPDATE courses SET is_deleted = true, deleted_at = current_timestamp(6) WHERE id = ?")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Course extends AuditableEntity implements AggregateRoot {

    @Column(nullable = false, length = 255)
    private String name;

    @Column(length = 1000)
    private String description;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "course_authors", joinColumns = @JoinColumn(name = "course_id"), inverseJoinColumns = @JoinColumn(name = "author_id"))
    private Set<Author> authors = new LinkedHashSet<>();

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("createdAt ASC")
    @JsonManagedReference(value = "course-sessions")
    private Set<Session> sessions = new LinkedHashSet<>();

    private Course(String name, Set<Author> authors) {
        setName(name);
        setAuthorsInternal(authors);
        enforceAtLeastOneAuthor();
    }

    public static Course create(String name, Set<Author> authors) {
        return new Course(name, authors);
    }

    public void addAuthor(Author author) {
        Objects.requireNonNull(author, "author");
        this.authors.add(author);
    }

    public void removeAuthor(Author author) {
        Objects.requireNonNull(author, "author");
        if (this.authors.size() == 1 && this.authors.contains(author)) {
            throw new IllegalStateException("Course must have at least one author");
        }
        this.authors.remove(author);
    }

    public void addSession(Session session) {
        Objects.requireNonNull(session, "session");
        this.sessions.add(session);
        session.setCourse(this);
    }

    public void removeSession(Session session) {
        Objects.requireNonNull(session, "session");
        if (this.sessions.size() == 1 && this.sessions.contains(session)) {
            throw new IllegalStateException("Course must have at least one session");
        }
        this.sessions.remove(session);
    }

    public void rename(String newName) {
        setName(newName);
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @PrePersist
    @PreUpdate
    protected void validateInvariant() {
        enforceAtLeastOneAuthor();
        enforceAtLeastOneSession();
    }

    private void setName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Course name is required");
        }
        if (name.length() > 255) {
            throw new IllegalArgumentException("Course name must be <= 255 characters");
        }
        this.name = name;
    }

    private void setAuthorsInternal(Set<Author> authors) {
        if (authors == null) {
            throw new IllegalArgumentException("authors collection is required");
        }
        this.authors.clear();
        this.authors.addAll(authors);
    }

    private void enforceAtLeastOneAuthor() {
        if (this.authors == null || this.authors.isEmpty()) {
            throw new IllegalStateException("Course must have at least one author");
        }
    }

    private void enforceAtLeastOneSession() {
        if (this.sessions == null || this.sessions.isEmpty()) {
            throw new IllegalStateException("Course must have at least one session");
        }
    }

    private void setSessionsInternal(Set<Session> sessions) {
        if (sessions == null) {
            throw new IllegalArgumentException("sessions collection is required");
        }
        this.sessions.clear();
        for (Session s : sessions) {
            addSession(s);
        }
    }

    public static Course create(String name, Set<Author> authors, Set<Session> sessions) {
        Course course = new Course(name, authors);
        course.setSessionsInternal(sessions);
        course.enforceAtLeastOneSession();
        return course;
    }
}
