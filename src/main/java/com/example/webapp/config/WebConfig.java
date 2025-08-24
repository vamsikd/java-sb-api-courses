package com.example.webapp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final CorsProperties corsProperties;
    private final CorrelationIdInterceptor correlationIdInterceptor;

    public WebConfig(CorsProperties corsProperties, CorrelationIdInterceptor correlationIdInterceptor) {
        this.corsProperties = corsProperties;
        this.correlationIdInterceptor = correlationIdInterceptor;
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins(corsProperties.getAllowedOrigins())
                .allowedMethods(corsProperties.getAllowedMethods())
                .allowedHeaders(corsProperties.getAllowedHeaders())
                .allowCredentials(corsProperties.getAllowedCredentials())
                .maxAge(corsProperties.getMaxAge());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(correlationIdInterceptor);
    }
}
