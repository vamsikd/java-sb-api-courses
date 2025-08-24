package com.example.webapp.application.author.dto;

import com.example.webapp.application.common.dto.BaseAuditableDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthorDto extends BaseAuditableDto {
    private Long id;
    private String firstname;
    private String lastname;
    private String email;
}
