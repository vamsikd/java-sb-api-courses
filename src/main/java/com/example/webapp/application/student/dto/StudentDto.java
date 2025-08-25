package com.example.webapp.application.student.dto;

import com.example.webapp.application.common.dto.BaseAuditableDto;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class StudentDto extends BaseAuditableDto {
    private Long id;
    private String firstname;
    private String lastname;
    private String email;
    private Short age;
    private StudentProfileDto profile;
}