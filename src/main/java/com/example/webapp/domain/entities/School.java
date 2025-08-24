package com.example.webapp.domain.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;

@Entity
@Table(name = "schools")
public class School {
    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private String address;
    @OneToMany(mappedBy = "school", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Student> students;

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }
}
