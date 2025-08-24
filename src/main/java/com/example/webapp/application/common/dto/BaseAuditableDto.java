package com.example.webapp.application.common.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public abstract class BaseAuditableDto {
    private Instant createdAt;
    private Instant updatedAt;
    private Boolean deleted;
    private Instant deletedAt;
}
