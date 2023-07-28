package com.example.restapi.event.contorller;

import com.example.restapi.SecurityConfig;
import com.example.restapi.TestDescription;
import com.example.restapi.event.domain.Event;
import com.example.restapi.event.domain.EventDto;
import com.example.restapi.event.domain.EventStatus;
import com.example.restapi.event.repository.EventRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDateTime;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
/*@WebMvcTest(
        controllers = EventController.class,
        excludeAutoConfiguration = SecurityAutoConfiguration.class,
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)
        }
)*/
class EventControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void test() throws Exception {
        mockMvc.perform(get("/event"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Create Event 테스트")
    void createEvent() throws Exception {
        EventDto event = EventDto.builder()
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

        mockMvc.perform(post("/api/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaTypes.HAL_JSON)
                        .content(objectMapper.writeValueAsString(event)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().exists(HttpHeaders.LOCATION))
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_VALUE))
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("id").value(Matchers.not(100)))
                .andExpect(jsonPath("free").value(false))
                .andExpect(jsonPath("offline").value(true))
                .andExpect(jsonPath("eventStatus").value(EventStatus.DRAFT.name()))
        ;
    }

    @Test
    @DisplayName("Create Event 실패 테스트")
    void createEvent_BadRequest() throws Exception {
        Event event = Event.builder()
                .id(100)
                .name("event_1")
                .description("event desc")
                .beginEnrollmentDateTime(LocalDateTime.of(2023, 7, 27, 18, 0, 0))
                .closeEnrollmentDateTime(LocalDateTime.of(2023, 7, 28, 18, 0, 0))
                .beginEventDateTime(LocalDateTime.of(2023, 7, 27, 18, 0, 0))
                .endEventDateTime(LocalDateTime.of(2023, 7, 28, 18, 0, 0))
                .basePrice(100)
                .maxPrice(200)
                .limitOfEnrollment(100)
                .free(true)
                .location("평택")
                .build();

        mockMvc.perform(post("/api/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaTypes.HAL_JSON)
                        .content(objectMapper.writeValueAsString(event)))
                .andDo(print())
                .andExpect(status().isBadRequest())
        ;
    }

    @Test
    @DisplayName("Create Event 실패 테스트 (빈값)")
    void createEvent_BadRequest_Empty() throws Exception {
        EventDto event = EventDto.builder().build();

        mockMvc.perform(post("/api/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaTypes.HAL_JSON)
                        .content(objectMapper.writeValueAsString(event)))
                .andDo(print())
                .andExpect(status().isBadRequest())
        ;
    }

    @Test
    @DisplayName("Create Event 실패 테스트 (타입에러)")
    void createEvent_BadRequest_TypeError() throws Exception {
        EventDto event = EventDto.builder()
                .name("event_1")
                .description("event desc")
                .beginEnrollmentDateTime(LocalDateTime.of(2023, 7, 29, 18, 0, 0))
                .closeEnrollmentDateTime(LocalDateTime.of(2023, 7, 28, 18, 0, 0))
                .beginEventDateTime(LocalDateTime.of(2023, 7, 29, 18, 0, 0))
                .endEventDateTime(LocalDateTime.of(2023, 7, 28, 18, 0, 0))
                .basePrice(1000)
                .maxPrice(200)
                .limitOfEnrollment(100)
                .location("평택")
                .build();

        MvcResult mvcResult = mockMvc.perform(post("/api/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaTypes.HAL_JSON)
                        .content(objectMapper.writeValueAsString(event)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].objectName").exists())
                .andExpect(jsonPath("$[0].code").exists())
                .andExpect(jsonPath("$[0].defaultMessage").exists())
                .andReturn();

        System.out.println(mvcResult);

    }

}