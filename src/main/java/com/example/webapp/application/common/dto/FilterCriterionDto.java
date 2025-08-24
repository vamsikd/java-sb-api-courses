package com.example.webapp.application.common.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record FilterCriterionDto(
        @NotBlank String field,
        @NotNull FilterOp op,
        @NotBlank String value) {
}
