package com.example.webapp.domain.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Table(name = "students")
@Getter
@Setter
@NoArgsConstructor
@SQLDelete(sql = "UPDATE students SET is_deleted = true, deleted_at = current_timestamp(6) WHERE id = ?")
@SQLRestriction("is_deleted = false")
public class Student extends AuditableEntity implements AggregateRoot {

    private String firstname;
    private String lastname;
    @Column(unique = true)
    private String email;
    private Short age;

    @OneToOne(mappedBy = "student", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private StudentProfile profile;

}
