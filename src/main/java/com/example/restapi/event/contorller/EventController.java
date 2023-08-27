package com.example.restapi.event.contorller;


import com.example.restapi.App;
import com.example.restapi.event.domain.Event;
import com.example.restapi.event.domain.EventDto;
import com.example.restapi.event.repository.EventRepository;
import com.example.restapi.event.validator.EventValidator;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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

    /**
     * @param assembler JPA가 지원해주는 페이지 링크 만들어주는 리소스 객체
     */
    @GetMapping
    public ResponseEntity getEvents(Pageable pageable, PagedResourcesAssembler<Event> assembler) {
        Page<Event> page = this.eventRepository.findAll(pageable);
        var pagedModel = assembler.toModel(page, entity -> {
            entity.add(linkTo(EventController.class).slash(entity.getId()).withSelfRel());
            return entity;
        });
        pagedModel.add(linkTo(App.class).slash("docs").slash("index.html#resources-events-list").withRel("profile"));

        return ResponseEntity.ok(pagedModel);
    }

    @PostMapping("/{id}")
    public ResponseEntity getEvent(@PathVariable Integer id) {
        Optional<Event> byId = this.eventRepository.findById(id);

        if (byId.isEmpty()) {
            // throw를 내고 exception hanlder controller한테 위임시키는게 나은듯
            Map map = new HashMap();
            map.put("Error-Message", "can not found event id");
            return ResponseEntity.badRequest()
                    .body(
                            EntityModel.of(map, linkTo(methodOn(IndexController.class).index()).withRel("index"))
                    );
        }

        Event newEvent = byId.get();

        WebMvcLinkBuilder selfLink = linkTo(EventController.class)
                .slash(newEvent.getId());

        newEvent.add(linkTo(EventController.class).withRel("query-events"));
        newEvent.add(selfLink.withSelfRel());
        newEvent.add(selfLink.withRel("update-event"));
        newEvent.add(linkTo(App.class).slash("docs").slash("index.html#resources-events-get").withRel("profile"));

        return ResponseEntity.ok().body(newEvent);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateEvent(@PathVariable Integer id, @RequestBody @Valid EventDto eventDto, Errors errors) {
        Optional<Event> optionalEvent = this.eventRepository.findById(id);

        if (optionalEvent.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        log.info("{}", errors);

        EntityModel<Errors> errorsEntityModel = EntityModel.of(errors,
                linkTo(methodOn(IndexController.class).index()).withRel("index"));

        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(errorsEntityModel);
        }
        // BeanPropertyBindingResult - Errors 타입
        eventValidator.validate(eventDto, errors);
        log.info("{}", errors);

        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(EntityModel.of(errorsEntityModel));
        }


        Event event = optionalEvent.get();
        modelMapper.map(eventDto, event);
        Event newEvent = this.eventRepository.save(event);

        newEvent.add(linkTo(EventController.class).withRel("query-events"));
        newEvent.add(linkTo(EventController.class).slash(newEvent.getId()).withSelfRel());
        newEvent.add(linkTo(EventController.class).slash(newEvent.getId()).withRel("get-event"));
        newEvent.add(linkTo(App.class).slash("docs").slash("index.html#resources-events-update").withRel("profile"));

        return ResponseEntity.ok().body(newEvent);
    }

    @PostMapping
    public ResponseEntity createEvent(@RequestBody @Valid EventDto eventDto, Errors errors) {
        log.info("{}", errors);

        EntityModel<Errors> errorsEntityModel = EntityModel.of(errors,
                linkTo(methodOn(IndexController.class).index()).withRel("index"));

        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(errorsEntityModel);
        }

        eventValidator.validate(eventDto, errors);
        log.info("{}", errors);

        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(EntityModel.of(errorsEntityModel));
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
