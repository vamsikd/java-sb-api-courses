package com.example.webapp.application.common.dto;

import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Minimal pagination payload for API responses.
 */
public record PageDto<T>(
        List<T> content,
        int page,
        int size,
        long totalElements,
        int totalPages,
        boolean first,
        boolean last,
        boolean empty) {
    public static <T> PageDto<T> from(Page<T> page) {
        return new PageDto<>(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isFirst(),
                page.isLast(),
                page.isEmpty());
    }
}
