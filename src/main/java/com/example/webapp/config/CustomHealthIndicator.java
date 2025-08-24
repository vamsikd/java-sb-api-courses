package com.example.webapp.config;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class CustomHealthIndicator implements HealthIndicator {

    @Override
    public Health health() {
        // In a real application, you would perform a check here,
        // e.g., connectivity to an external service, database, or custom component status.
        // For demonstration, we'll just return UP.
        boolean isServiceUp = true; // Replace with actual check

        if (isServiceUp) {
            return Health.up()
                    .withDetail("customService", "Available")
                    .build();
        } else {
            return Health.down()
                    .withDetail("customService", "Not Available")
                    .withDetail("error", "Service dependency failed")
                    .build();
        }
    }
}
