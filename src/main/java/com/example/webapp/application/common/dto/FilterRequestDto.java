package com.example.webapp.application.common.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;

public record FilterRequestDto(
        @NotEmpty @Valid List<FilterCriterionDto> criteria,
        // AND or OR (default AND if null/blank)
        String combinator) {
    public String normalizedCombinator() {
        return (combinator == null || combinator.isBlank()) ? "AND" : combinator.trim().toUpperCase();
    }
}
