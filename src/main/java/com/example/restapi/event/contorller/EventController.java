package com.example.restapi.event.contorller;


import com.example.restapi.App;
import com.example.restapi.event.domain.Event;
import com.example.restapi.event.domain.EventDto;
import com.example.restapi.event.repository.EventRepository;
import com.example.restapi.event.validator.EventValidator;
import jakarta.validation.Valid;
import jakarta.validation.Validation;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.LinkRelation;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Slf4j
@RestController
@RequestMapping(
        value = "/api/events",
        produces = MediaTypes.HAL_JSON_VALUE // 응답 타입 명시
)
public class EventController {
    private final EventRepository eventRepository;
    private final ModelMapper modelMapper;

    @Autowired
    private EventValidator eventValidator;

    public EventController(EventRepository eventRepository, ModelMapper modelMapper) {
        this.eventRepository = eventRepository;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/event")
    public ResponseEntity event() {
        return ResponseEntity.ok("Hello");
    }

    @PostMapping
    public ResponseEntity createEvent(@RequestBody @Valid EventDto eventDto, Errors errors) {
        log.info("{}", errors);

        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(errors);
        }

        eventValidator.validate(eventDto, errors);
        log.info("{}", errors);

        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(errors);
        }

        Event event = this.modelMapper.map(eventDto, Event.class);
        event.update();

        Event newEvent = this.eventRepository.save(event);

        WebMvcLinkBuilder selfLink = linkTo(EventController.class)
                .slash(newEvent.getId());

        URI uri = selfLink.toUri();

        newEvent.add(linkTo(EventController.class).withRel("query-events"));
        newEvent.add(selfLink.withSelfRel());
        newEvent.add(selfLink.withRel("update-event"));
        newEvent.add(linkTo(App.class).slash("docs").slash("index.html#resources-events-create").withRel("profile"));


        return ResponseEntity.created(uri).body(newEvent);
    }
}
