package com.example.restapi.event.domain;

import com.example.restapi.account.domain.Account;
import com.example.restapi.event.contorller.EventController;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
public class Event extends RepresentationModel<Event> {
    /*
    Serializer <-> Deserializer 동작 시 Object를 풀어서 진행해줌.
    @JsonUnwrapped
    private Event _event;
    */

    @Id
    @GeneratedValue
    private Integer id;

    private String name;
    private String description;
    private LocalDateTime beginEnrollmentDateTime;
    private LocalDateTime closeEnrollmentDateTime;
    private LocalDateTime beginEventDateTime;
    private LocalDateTime endEventDateTime;
    private String location; // (optional) 이게 없으면 온라인 모임
    private int basePrice; // (optional)
    private int maxPrice; // (optional)
    private int limitOfEnrollment;
    private boolean offline = false;
    private boolean free = false;

    @Enumerated(EnumType.STRING)
    private EventStatus eventStatus = EventStatus.DRAFT;

    @ManyToOne
    private Account account;

    public void update() {
        if (this.basePrice == 0 && this.maxPrice == 0) {
            this.free = true;
        } else {
            this.free = false;
        }

        if (this.location == null || this.location.isBlank()) {
            this.offline = false;
        } else {
            this.offline = true;
        }
    }
}
