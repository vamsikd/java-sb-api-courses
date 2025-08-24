package com.example.webapp.domain.events;

import com.example.webapp.application.author.dto.AuthorDto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class AuthorCreatedEvent extends ApplicationEvent {

    private AuthorDto authorDto;

    public AuthorCreatedEvent(Object source, AuthorDto authorDto) {
        super(source);
        this.authorDto = authorDto;
    }
}
