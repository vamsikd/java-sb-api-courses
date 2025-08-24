package com.example.webapp.application.school;

import com.example.webapp.application.school.dto.*;
import java.util.List;

public interface SchoolService {
    List<SchoolDto> getAllSchools();

    SchoolDto getSchoolById(Integer id);

    SchoolDto createSchool(CreateSchoolDto createDto);

    SchoolDto updateSchool(Integer id, UpdateSchoolDto updateDto);

    void deleteSchool(Integer id);
}
