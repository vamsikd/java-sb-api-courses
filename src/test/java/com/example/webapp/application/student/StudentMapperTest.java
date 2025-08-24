package com.example.webapp.application.student;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.webapp.application.school.SchoolRepository;
import com.example.webapp.application.student.dto.*;
import com.example.webapp.domain.entities.*;

@ExtendWith(MockitoExtension.class)
class StudentMapperTest {

    @Mock
    private SchoolRepository schoolRepository;

    private StudentMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new StudentMapper(schoolRepository);
    }

    @Test
    void toStudent_mapsFields_andSetsSchool_whenSchoolIdProvided() {
        // arrange
        Integer schoolId = 10;
        School schoolRef = new School();
        schoolRef.setId(schoolId);
        when(schoolRepository.getReferenceById(schoolId)).thenReturn(schoolRef);

        CreateStudentDto dto = new CreateStudentDto(
                "John",
                "Doe",
                "john.doe@example.com",
                (short) 20,
                schoolId);

        // act
        Student student = mapper.toStudent(dto);

        // assert
        assertEquals("John", student.getFirstname());
        assertEquals("Doe", student.getLastname());
        assertEquals("john.doe@example.com", student.getEmail());
        assertEquals(Short.valueOf((short) 20), student.getAge());
        assertNotNull(student.getSchool());
        assertEquals(schoolId, student.getSchool().getId());
        verify(schoolRepository).getReferenceById(schoolId);
    }

    @Test
    void toStudent_mapsFields_andLeavesSchoolNull_whenNoSchoolId() {
        // arrange
        CreateStudentDto dto = new CreateStudentDto(
                "Jane",
                "Smith",
                "jane.smith@example.com",
                (short) 25,
                null);

        // act
        Student student = mapper.toStudent(dto);

        // assert
        assertEquals("Jane", student.getFirstname());
        assertEquals("Smith", student.getLastname());
        assertEquals("jane.smith@example.com", student.getEmail());
        assertEquals(Short.valueOf((short) 25), student.getAge());
        assertNull(student.getSchool());
        verify(schoolRepository, never()).getReferenceById(any());
    }

    @Test
    void toStudentDto_mapsFields_andExposesSchoolId_whenSchoolPresent() {
        // arrange
        Student student = new Student();
        student.setId(1);
        student.setFirstname("Alice");
        student.setLastname("Lee");
        student.setEmail("alice.lee@example.com");
        student.setAge((short) 30);
        School school = new School();
        school.setId(5);
        student.setSchool(school);

        // act
        StudentDto dto = mapper.toStudentDto(student);

        // assert
        assertEquals(1, dto.id());
        assertEquals("Alice", dto.firstname());
        assertEquals("Lee", dto.lastname());
        assertEquals("alice.lee@example.com", dto.email());
        assertEquals(Short.valueOf((short) 30), dto.age());
        assertEquals(5, dto.schoolId());
    }

    @Test
    void toStudentDto_mapsFields_andNullSchoolId_whenNoSchool() {
        // arrange
        Student student = new Student();
        student.setId(2);
        student.setFirstname("Bob");
        student.setLastname("Kim");
        student.setEmail("bob.kim@example.com");
        student.setAge((short) 22);
        student.setSchool(null);

        // act
        StudentDto dto = mapper.toStudentDto(student);

        // assert
        assertEquals(2, dto.id());
        assertEquals("Bob", dto.firstname());
        assertEquals("Kim", dto.lastname());
        assertEquals("bob.kim@example.com", dto.email());
        assertEquals(Short.valueOf((short) 22), dto.age());
        assertNull(dto.schoolId());
    }

    @Test
    void updateStudent_updatesFields_convertsAge_andSetsSchool_whenProvided() {
        // arrange
        Student existing = new Student();
        existing.setFirstname("Old");
        existing.setLastname("Name");
        existing.setEmail("old@example.com");
        existing.setAge((short) 40);

        Integer newSchoolId = 7;
        School schoolRef = new School();
        schoolRef.setId(newSchoolId);
        when(schoolRepository.getReferenceById(newSchoolId)).thenReturn(schoolRef);

        UpdateStudentDto dto = new UpdateStudentDto(
                "New",
                "Name",
                "new@example.com",
                18,
                newSchoolId);

        // act
        Student updated = mapper.toStudent(dto, existing);

        // assert
        assertSame(existing, updated);
        assertEquals("New", existing.getFirstname());
        assertEquals("Name", existing.getLastname());
        assertEquals("new@example.com", existing.getEmail());
        assertEquals(Short.valueOf((short) 18), existing.getAge());
        assertNotNull(existing.getSchool());
        assertEquals(newSchoolId, existing.getSchool().getId());
        verify(schoolRepository).getReferenceById(newSchoolId);
    }

    @Test
    void updateStudent_setsAgeNull_whenAgeIsNull_andDoesNotTouchSchool_whenNoSchoolId() {
        // arrange
        Student existing = new Student();
        existing.setFirstname("Old");
        existing.setLastname("Name");
        existing.setEmail("old@example.com");
        existing.setAge((short) 40);
        existing.setSchool(null);

        UpdateStudentDto dto = new UpdateStudentDto(
                "A",
                "B",
                "a.b@example.com",
                null,
                null);

        // act
        Student updated = mapper.toStudent(dto, existing);

        // assert
        assertSame(existing, updated);
        assertEquals("A", existing.getFirstname());
        assertEquals("B", existing.getLastname());
        assertEquals("a.b@example.com", existing.getEmail());
        assertNull(existing.getAge());
        assertNull(existing.getSchool());
        verify(schoolRepository, never()).getReferenceById(any());
    }
}
