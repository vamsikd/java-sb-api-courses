package com.example.webapp.domain.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;

import java.time.LocalDateTime;

@Entity
@Table(name = "sessions", indexes = {
        @Index(name = "ix_sessions_is_deleted", columnList = "is_deleted")
})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE sessions SET is_deleted = true, deleted_at = current_timestamp(6) WHERE id = ?")
public class Session extends AuditableEntity {

    @Column(nullable = false, length = 255)
    private String title;

    @Column(name = "starts_at", nullable = true)
    private LocalDateTime startsAt;

    @Column(name = "ends_at", nullable = true)
    private LocalDateTime endsAt;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "course_id", nullable = false)
    @JsonBackReference(value = "course-sessions")
    private Course course;

    private Session(String title, LocalDateTime startsAt, LocalDateTime endsAt) {
        setTitle(title);
        this.startsAt = startsAt;
        this.endsAt = endsAt;
    }

    public static Session create(String title) {
        return new Session(title, null, null);
    }

    public static Session schedule(String title, LocalDateTime startsAt, LocalDateTime endsAt) {
        return new Session(title, startsAt, endsAt);
    }

    void setCourse(Course course) {
        this.course = course;
    }

    public void rename(String newTitle) {
        setTitle(newTitle);
    }

    private void setTitle(String title) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Session title is required");
        }
        if (title.length() > 255) {
            throw new IllegalArgumentException("Session title must be <= 255 characters");
        }
        this.title = title;
    }
}
