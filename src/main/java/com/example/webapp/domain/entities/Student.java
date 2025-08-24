package com.example.webapp.domain.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;

@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue
    private Integer id;
    private String firstname;
    private String lastname;
    @Column(unique = true)
    private String email;
    private Short age;

    @ManyToOne
    @JoinColumn(name = "school_id")
    @JsonBackReference
    private School school;
    @OneToOne(mappedBy = "student", cascade = CascadeType.ALL)
    private StudentProfile profile;

    public Student() {

    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Short getAge() {
        return age;
    }

    public void setAge(Short age) {
        this.age = age;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public StudentProfile getProfile() {
        return profile;
    }

    public void setProfile(StudentProfile profile) {
        this.profile = profile;
    }
}
