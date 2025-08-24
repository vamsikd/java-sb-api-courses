package com.example.webapp.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "cors")
@Data
public class CorsProperties {

    private String[] allowedOrigins = {};
    private String[] allowedMethods = { "GET", "POST", "PUT", "DELETE", "OPTIONS" };
    private String[] allowedHeaders = { "Authorization", "Content-Type" };
    private Boolean allowedCredentials;
    private long maxAge = 3600;
}
