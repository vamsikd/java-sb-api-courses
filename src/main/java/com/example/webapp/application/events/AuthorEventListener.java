package com.example.webapp.application.events;

import com.example.webapp.domain.events.AuthorCreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class AuthorEventListener {

    private static final Logger log = LoggerFactory.getLogger(AuthorEventListener.class);

    @EventListener
    public void handleAuthorCreatedEvent(AuthorCreatedEvent event) {
        log.info("AuthorCreatedEvent received for author ID: {} and email: {}",
                event.getAuthorDto().getId(), event.getAuthorDto().getEmail());
        // Here you can add further business logic that reacts to an author being created,
        // e.g., sending a welcome email, updating a search index, etc.
    }
}
