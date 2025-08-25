package com.example.webapp.application.student;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.webapp.application.student.dto.*;
import com.example.webapp.domain.entities.*;

@ExtendWith(MockitoExtension.class)
class StudentMapperTest {

    private StudentMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new StudentMapper();
    }

    @Test
    void toEntity_mapsFields() {
        // arrange
        CreateStudentDto dto = new CreateStudentDto(
                "Jane",
                "Smith",
                "jane.smith@example.com",
                (short) 25,
                null);

        // act
        Student student = mapper.toEntity(dto);

        // assert
        assertEquals("Jane", student.getFirstname());
        assertEquals("Smith", student.getLastname());
        assertEquals("jane.smith@example.com", student.getEmail());
        assertEquals(Short.valueOf((short) 25), student.getAge());
    }

    @Test
    void toDto_mapsFields() {
        // arrange
        Student student = new Student();
        student.setId(2L);
        student.setFirstname("Bob");
        student.setLastname("Kim");
        student.setEmail("bob.kim@example.com");
        student.setAge((short) 22);

        // act
        StudentDto dto = mapper.toDto(student);

        // assert
        assertEquals(2L, dto.getId());
        assertEquals("Bob", dto.getFirstname());
        assertEquals("Kim", dto.getLastname());
        assertEquals("bob.kim@example.com", dto.getEmail());
        assertEquals(Short.valueOf((short) 22), dto.getAge());
    }

    @Test
    void updateEntity_updatesFields() {
        // arrange
        Student existing = new Student();
        existing.setFirstname("Old");
        existing.setLastname("Name");
        existing.setEmail("old@example.com");
        existing.setAge((short) 40);

        UpdateStudentDto dto = new UpdateStudentDto(
                "A",
                "B",
                "a.b@example.com",
                null,
                null);

        // act
        mapper.updateEntity(dto, existing);

        // assert
        assertEquals("A", existing.getFirstname());
        assertEquals("B", existing.getLastname());
        assertEquals("a.b@example.com", existing.getEmail());
        assertNull(existing.getAge());
    }
}
