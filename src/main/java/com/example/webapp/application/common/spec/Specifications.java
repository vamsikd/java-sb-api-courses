package com.example.webapp.application.common.spec;

import com.example.webapp.application.common.dto.FilterCriterionDto;
import com.example.webapp.application.common.dto.FilterOp;
import com.example.webapp.application.common.dto.FilterRequestDto;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Root;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Locale;

public final class Specifications {
    private Specifications() {
    }

    // Simple helper to build a case-insensitive contains filter for String fields
    public static <T> Specification<T> containsIgnoreCase(String field, String value) {
        if (value == null || value.isBlank())
            return null;
        final String pattern = "%" + value.toLowerCase(java.util.Locale.ROOT) + "%";
        return (root, query, cb) -> cb.like(cb.lower(root.get(field)), pattern);
    }

    public static <T> Specification<T> from(FilterRequestDto request) {
        if (request == null || request.criteria() == null || request.criteria().isEmpty()) {
            return (root, query, cb) -> cb.conjunction();
        }

        Specification<T> combined = null;
        final boolean useOr = "OR".equalsIgnoreCase(request.normalizedCombinator());

        for (FilterCriterionDto c : request.criteria()) {
            Specification<T> spec = criterionToSpec(c);
            if (combined == null) {
                combined = spec;
            } else {
                combined = useOr ? combined.or(spec) : combined.and(spec);
            }
        }
        return combined == null ? (root, query, cb) -> cb.conjunction() : combined;
    }

    private static <T> Specification<T> criterionToSpec(FilterCriterionDto c) {
        final String field = c.field();
        final FilterOp op = c.op();
        final String rawValue = c.value();

        return (root, query, cb) -> {
            Path<?> path = resolvePath(root, field);
            Class<?> javaType = path.getJavaType();
            Object typedValue = convert(rawValue, javaType);

            switch (op) {
                case EQUALS -> {
                    if (javaType == String.class) {
                        Expression<String> lhs = cb.lower(path.as(String.class));
                        return cb.equal(lhs, rawValue.toLowerCase(Locale.ROOT));
                    }
                    return cb.equal(path, typedValue);
                }
                case GREATER_THAN -> {
                    if (!Comparable.class.isAssignableFrom(javaType)) {
                        throw new IllegalArgumentException("Field '" + field + "' is not comparable");
                    }
                    return greaterThan(cb, path, typedValue);
                }
                case LESS_THAN -> {
                    if (!Comparable.class.isAssignableFrom(javaType)) {
                        throw new IllegalArgumentException("Field '" + field + "' is not comparable");
                    }
                    return lessThan(cb, path, typedValue);
                }
                case CONTAINS -> {
                    ensureString(field, javaType);
                    Expression<String> lhs = cb.lower(path.as(String.class));
                    String val = "%" + rawValue.toLowerCase(Locale.ROOT) + "%";
                    return cb.like(lhs, val);
                }
                case STARTS_WITH -> {
                    ensureString(field, javaType);
                    Expression<String> lhs = cb.lower(path.as(String.class));
                    String val = rawValue.toLowerCase(Locale.ROOT) + "%";
                    return cb.like(lhs, val);
                }
                case ENDS_WITH -> {
                    ensureString(field, javaType);
                    Expression<String> lhs = cb.lower(path.as(String.class));
                    String val = "%" + rawValue.toLowerCase(Locale.ROOT);
                    return cb.like(lhs, val);
                }
                default -> throw new IllegalArgumentException("Unsupported op: " + op);
            }
        };
    }

    private static void ensureString(String field, Class<?> type) {
        if (type != String.class) {
            throw new IllegalArgumentException("Field '" + field + "' must be a string for this operation");
        }
    }

    private static Path<?> resolvePath(Root<?> root, String field) {
        // Support nested with dot notation: e.g., "school.name"
        String[] parts = field.split("\\.");
        Path<?> path = root;
        for (String p : parts) {
            path = path.get(p);
        }
        return path;
    }

    private static Object convert(String value, Class<?> targetType) {
        if (value == null)
            return null;
        if (targetType == String.class)
            return value;
        if (targetType == Integer.class || targetType == int.class)
            return Integer.valueOf(value);
        if (targetType == Long.class || targetType == long.class)
            return Long.valueOf(value);
        if (targetType == Double.class || targetType == double.class)
            return Double.valueOf(value);
        if (targetType == BigDecimal.class)
            return new BigDecimal(value);
        if (targetType == Boolean.class || targetType == boolean.class)
            return Boolean.valueOf(value);
        if (targetType == Instant.class)
            return Instant.parse(value);
        if (targetType == LocalDate.class)
            return LocalDate.parse(value);
        if (targetType == LocalDateTime.class)
            return LocalDateTime.parse(value);
        // Fallback: try string, else error
        throw new IllegalArgumentException("Unsupported type conversion for: " + targetType.getSimpleName());
    }

    @SuppressWarnings({ "unchecked" })
    private static <Y extends Comparable<? super Y>> jakarta.persistence.criteria.Predicate greaterThan(
            jakarta.persistence.criteria.CriteriaBuilder cb,
            Path<?> path,
            Object value) {
        Expression<Y> expr = (Expression<Y>) path;
        return cb.greaterThan(expr, (Y) value);
    }

    @SuppressWarnings({ "unchecked" })
    private static <Y extends Comparable<? super Y>> jakarta.persistence.criteria.Predicate lessThan(
            jakarta.persistence.criteria.CriteriaBuilder cb,
            Path<?> path,
            Object value) {
        Expression<Y> expr = (Expression<Y>) path;
        return cb.lessThan(expr, (Y) value);
    }
}
