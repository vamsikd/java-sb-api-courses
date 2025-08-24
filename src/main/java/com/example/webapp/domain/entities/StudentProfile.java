package com.example.webapp.domain.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "student_profiles")
public class StudentProfile {
    @Id
    @GeneratedValue
    private Integer id;
    private String bio;
    private String profilePictureUrl;
    @OneToOne
    private Student student;

    public StudentProfile() {

    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getProfilePictureUrl() {
        return profilePictureUrl;
    }

    public void setProfilePictureUrl(String profilePictureUrl) {
        this.profilePictureUrl = profilePictureUrl;
    }

    public Student getStudent() {
        return student;
    }
}
