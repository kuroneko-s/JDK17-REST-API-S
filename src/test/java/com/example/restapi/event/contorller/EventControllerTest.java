package com.example.restapi.event.contorller;

import com.example.restapi.event.domain.Event;
import com.example.restapi.event.repository.EventRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.access.SecurityConfig;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(
        controllers = EventController.class,
        excludeAutoConfiguration = SecurityAutoConfiguration.class,
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)
        }
)
class EventControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    EventRepository eventRepository;

    @Test
    void test() throws Exception {
        mockMvc.perform(get("/event"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Create Event 테스트")
    void createEvent() throws Exception {
        Event event = Event.builder()
                .name("event_1")
                .description("event desc")
                .beginEnrollmentDateTime(LocalDateTime.of(2023, 7, 27, 18, 0, 0))
                .closeEnrollmentDateTime(LocalDateTime.of(2023, 7, 28, 18, 0, 0))
                .beginEventDateTime(LocalDateTime.of(2023, 7, 27, 18, 0, 0))
                .endEventDateTime(LocalDateTime.of(2023, 7, 28, 18, 0, 0))
                .basePrice(100)
                .maxPrice(200)
                .limitOfEnrollment(100)
                .location("평택")
                .build();
        event.setId(1);

        Mockito.when(eventRepository.save(event)).thenReturn(event);

        mockMvc.perform(post("/api/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaTypes.HAL_JSON)
                        .content(objectMapper.writeValueAsString(event)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().exists(HttpHeaders.LOCATION))
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_VALUE))
                .andExpect(jsonPath("id").exists());
    }

}