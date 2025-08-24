package com.example.webapp.domain.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;

@Entity
@Table(name = "authors")
@Getter
@Setter
@NoArgsConstructor
@SQLDelete(sql = "UPDATE authors SET is_deleted = true, deleted_at = current_timestamp(6) WHERE id = ?")
public class Author extends AuditableEntity {
    @Column(nullable = false, length = 100)
    private String firstname;
    @Column(nullable = false, length = 100)
    private String lastname;
    @Column(nullable = false, length = 320)
    private String email;
}
