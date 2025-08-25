package com.example.webapp.domain.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;

@Entity
@Table(name = "student_profiles")
@Getter
@Setter
@NoArgsConstructor
@SQLDelete(sql = "UPDATE student_profiles SET is_deleted = true, deleted_at = current_timestamp(6) WHERE id = ?")
public class StudentProfile extends AuditableEntity {

    private String bio;
    private String profilePictureUrl;

    @OneToOne
    @JoinColumn(name = "student_id", nullable = false, unique = true)
    private Student student;

}
