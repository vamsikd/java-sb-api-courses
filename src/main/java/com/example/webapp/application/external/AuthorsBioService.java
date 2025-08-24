package com.example.webapp.application.external;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class AuthorsBioService {

    private static final Logger log = LoggerFactory.getLogger(AuthorsBioService.class);
    private final WebClient webClient;

    public AuthorsBioService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://baconipsum.com").build();
    }

    public Mono<String> fetchRandomBio() {
        log.info("Fetching random bio from external API");
        return webClient.get()
                .uri("/api/?type=all-meat&paras=1&start-with-lorem=1")
                .retrieve()
                .bodyToMono(String[].class)
                .map(bios -> {
                    if (bios != null && bios.length > 0) {
                        return bios[0];
                    } else {
                        log.warn("Fetched empty bio array from external API.");
                        return ""; // Return empty string or handle as appropriate
                    }
                })
                .doOnSuccess(bio -> log.info("Successfully fetched bio: {}", bio.substring(0, Math.min(bio.length(), 50)) + "..."))
                .doOnError(error -> log.error("Error fetching bio: {}", error.getMessage()));
    }
}
