package com.example.webapp.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(title = "Course Platform API", version = "v1.0", description = "REST API for the Course Platform. Provides endpoints for course management, enrollment, and user authentication.", contact = @Contact(name = "D Vamsi Krishna", email = "vamsi.doddapaneni@outlook.com"), license = @License(name = "Apache 2.0", url = "http://www.apache.org/licenses/LICENSE-2.0.html")), servers = {
        @Server(url = "http://localhost:8080", description = "Local Development Server"),
        @Server(url = "https://api.example.com", description = "Production Server")
}, security = {
        @SecurityRequirement(name = "bearerAuth")
})
@SecurityScheme(name = "bearerAuth", type = SecuritySchemeType.HTTP, bearerFormat = "JWT", scheme = "bearer")
public class OpenApiConfig {
}
