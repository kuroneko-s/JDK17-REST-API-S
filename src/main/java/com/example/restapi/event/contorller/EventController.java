package com.example.restapi.event.contorller;


import com.example.restapi.event.domain.Event;
import com.example.restapi.event.repository.EventRepository;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping(
        value = "/api/events",
        produces = MediaTypes.HAL_JSON_VALUE // 응답 타입 명시
)
public class EventController {
    private final EventRepository eventRepository;

    public EventController(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @GetMapping("/event")
    public ResponseEntity event() {
        return ResponseEntity.ok("Hello");
    }

    @PostMapping
    public ResponseEntity createEvent(@RequestBody Event event) {
        Event saved = this.eventRepository.save(event);

        URI uri = linkTo(EventController.class)
                .slash(saved.getId())
                .toUri();

        return ResponseEntity.created(uri).body(saved);
    }
}
