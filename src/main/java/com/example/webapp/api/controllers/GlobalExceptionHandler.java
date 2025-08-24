package com.example.webapp.api.controllers;

import java.net.URI;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ProblemDetail> handleNotFound(EntityNotFoundException ex, HttpServletRequest request) {
        ProblemDetail pd = problem(HttpStatus.NOT_FOUND, "Resource not found", ex.getMessage(), request);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(pd);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ProblemDetail> handleValidation(MethodArgumentNotValidException ex,
            HttpServletRequest request) {
        ProblemDetail pd = problem(HttpStatus.BAD_REQUEST, "Validation failed",
                "One or more validation errors occurred", request);
        List<Violation> violations = ex.getBindingResult().getFieldErrors().stream()
                .map(err -> new Violation(err.getField(), err.getDefaultMessage()))
                .collect(Collectors.toList());
        pd.setProperty("errors", violations);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(pd);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ProblemDetail> handleConstraintViolation(ConstraintViolationException ex,
            HttpServletRequest request) {
        ProblemDetail pd = problem(HttpStatus.BAD_REQUEST, "Validation failed",
                "One or more validation errors occurred", request);
        List<Violation> violations = ex.getConstraintViolations().stream()
                .map(v -> new Violation(v.getPropertyPath().toString(), v.getMessage()))
                .collect(Collectors.toList());
        pd.setProperty("errors", violations);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(pd);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ProblemDetail> handleTypeMismatch(MethodArgumentTypeMismatchException ex,
            HttpServletRequest request) {
        String detail = String.format("Parameter '%s' has invalid value '%s'", ex.getName(), ex.getValue());
        ProblemDetail pd = problem(HttpStatus.BAD_REQUEST, "Invalid parameter", detail, request);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(pd);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ProblemDetail> handleUnreadable(HttpMessageNotReadableException ex,
            HttpServletRequest request) {
        ProblemDetail pd = problem(HttpStatus.BAD_REQUEST, "Malformed JSON request",
                "Request body is not readable", request);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(pd);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ProblemDetail> handleIllegalArg(IllegalArgumentException ex, HttpServletRequest request) {
        ProblemDetail pd = problem(HttpStatus.BAD_REQUEST, "Invalid request", ex.getMessage(), request);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(pd);
    }

    // @ExceptionHandler(Exception.class)
    // public ResponseEntity<ProblemDetail> handleGeneric(Exception ex,
    // HttpServletRequest request) {
    // ProblemDetail pd = problem(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected
    // error",
    // "An unexpected error occurred", request);
    // return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(pd);
    // }

    private ProblemDetail problem(HttpStatus status, String title, String detail, HttpServletRequest req) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(status, detail);
        pd.setTitle(title);
        pd.setInstance(URI.create(req.getRequestURI()));
        // include a best-effort trace id for correlation
        pd.setProperty("traceId", resolveTraceId(req));
        // optional: stable type URL could point to API docs
        // pd.setType(URI.create("https://example.com/problems/" + status.value()));
        return pd;
    }

    private String resolveTraceId(HttpServletRequest req) {
        String id = headerOrNull(req, "X-Trace-Id");
        if (isBlank(id))
            id = headerOrNull(req, "X-Request-Id");
        if (isBlank(id))
            id = headerOrNull(req, "X-Correlation-Id");
        if (isBlank(id))
            id = MDC.get("traceId");
        if (isBlank(id))
            id = MDC.get("X-B3-TraceId");
        if (isBlank(id))
            id = UUID.randomUUID().toString();
        return id;
    }

    private static String headerOrNull(HttpServletRequest req, String name) {
        String v = req.getHeader(name);
        return isBlank(v) ? null : v;
    }

    private static boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }

    public static record Violation(String field, String message) {
    }
}
