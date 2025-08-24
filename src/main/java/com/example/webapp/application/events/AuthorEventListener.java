package com.example.webapp.application.events;

import com.example.webapp.application.author.AuthorRepository;
import com.example.webapp.application.external.AuthorsBioService;
import com.example.webapp.domain.entities.Author;
import com.example.webapp.domain.events.AuthorCreatedEvent;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class AuthorEventListener {

    private static final Logger log = LoggerFactory.getLogger(AuthorEventListener.class);

    private final AuthorRepository authorRepository;
    private final AuthorsBioService authorsBioService;

    public AuthorEventListener(AuthorRepository authorRepository, AuthorsBioService authorsBioService) {
        this.authorRepository = authorRepository;
        this.authorsBioService = authorsBioService;
    }

    @EventListener
    public void handleAuthorCreatedEvent(AuthorCreatedEvent event) {
        log.info("AuthorCreatedEvent received for author ID: {} and email: {}",
                event.getAuthorDto().getId(), event.getAuthorDto().getEmail());

        // Fetch bio from external service
        authorsBioService.fetchRandomBio()
                .subscribe(bio -> {
                    log.info("Fetched bio for author ID {}: {}", event.getAuthorDto().getId(), bio.substring(0, Math.min(bio.length(), 50)) + "...");

                    // Retrieve the author entity
                    Author author = authorRepository.findById(event.getAuthorDto().getId())
                            .orElseThrow(() -> new EntityNotFoundException("Author not found after event"));

                    // Update the author with the fetched bio
                    author.setBio(bio);

                    // Save the updated author
                    authorRepository.save(author);
                    log.info("Author ID {} updated with bio.", author.getId());
                }, error -> {
                    log.error("Error fetching or updating bio for author ID {}: {}", event.getAuthorDto().getId(), error.getMessage());
                });
    }
}
