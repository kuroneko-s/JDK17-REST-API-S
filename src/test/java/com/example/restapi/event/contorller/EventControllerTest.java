package com.example.restapi.event.contorller;

import com.example.restapi.RestDocsConfigure;
import com.example.restapi.event.domain.Event;
import com.example.restapi.event.domain.EventDto;
import com.example.restapi.event.domain.EventStatus;
import com.example.restapi.event.repository.EventRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Example;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
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
@AutoConfigureRestDocs
@Import(RestDocsConfigure.class)
@ActiveProfiles("test")
class EventControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private EventRepository eventRepository;

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
                .andExpect(jsonPath("_links.self").exists())
                .andExpect(jsonPath("_links.query-events").exists())
                .andExpect(jsonPath("_links.update-event").exists())
                .andExpect(jsonPath("_links.profile").exists())
                .andDo(document("create-event",
                        links(
                                linkWithRel("self").description("self link"),
                                linkWithRel("query-events").description("query-events link"),
                                linkWithRel("update-event").description("update-event link"),
                                linkWithRel("profile").description("self profile link")
                        ),
                        requestHeaders(
                                headerWithName(HttpHeaders.ACCEPT).description("Header accepty"),
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("Header contenttype")
                        ),
                        requestFields(
                                fieldWithPath("name").description("name description"),
                                fieldWithPath("description").description("description description"),
                                fieldWithPath("beginEnrollmentDateTime").description("beginEnrollmentDateTime description"),
                                fieldWithPath("closeEnrollmentDateTime").description("closeEnrollmentDateTime description"),
                                fieldWithPath("beginEventDateTime").description("beginEventDateTime description"),
                                fieldWithPath("endEventDateTime").description("endEventDateTime description"),
                                fieldWithPath("location").description("location description"),
                                fieldWithPath("basePrice").description("basePrice description"),
                                fieldWithPath("maxPrice").description("maxPrice description"),
                                fieldWithPath("limitOfEnrollment").description("limitOfEnrollment description")
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.LOCATION).description("Header name - locaion"),
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("Header name contenttype")
                        ),
                        relaxedResponseFields( // 일부분만 문서화 (prefix relaxted 붙이기)
                                fieldWithPath("id").description("고유 번호")
                        )
                ))
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
                .andExpect(jsonPath("errors[0].objectName").exists())
                .andExpect(jsonPath("errors[0].code").exists())
                .andExpect(jsonPath("errors[0].defaultMessage").exists())
                .andExpect(jsonPath("_links.index").exists())
                .andReturn();

        System.out.println(mvcResult);
    }

    @Test
    @DisplayName("10개 씩 조회")
    public void pageableTest() throws Exception {
        IntStream.range(0, 30).forEach(this::generateEvent);

        this.mockMvc.perform(get("/api/events")
                        .param("page", "1")
                        .param("size", "10")
                        .param("sort", "id,DESC")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("page").exists())
                .andExpect(jsonPath("_links").exists())
                .andDo(document("query-events"))
        ;
    }

    private void generateEvent(int i) {
        Event event = Event.builder()
                .name("event_" + i)
                .description("event desc " + i)
                .beginEnrollmentDateTime(LocalDateTime.of(2023, 7, 27, 18, 0, 0))
                .closeEnrollmentDateTime(LocalDateTime.of(2023, 7, 28, 18, 0, 0))
                .beginEventDateTime(LocalDateTime.of(2023, 7, 27, 18, 0, 0))
                .endEventDateTime(LocalDateTime.of(2023, 7, 28, 18, 0, 0))
                .basePrice(100)
                .maxPrice(200)
                .limitOfEnrollment(100)
                .build();

        eventRepository.save(event);
    }

    private void generateEvent() {
        IntStream.range(0, 30).forEach(this::generateEvent);
    }

    @Test
    @DisplayName("단건 조회")
    public void getEventOne() throws Exception {
        this.generateEvent();

        Optional<Event> newEvent = this.eventRepository.findById(1);

        this.mockMvc.perform(
                        post("/api/events/" + newEvent.orElseThrow(() -> new IllegalArgumentException("id 1 - 조회 실패")).getId())
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("_links.query-events").exists())
                .andExpect(jsonPath("_links.self").exists())
                .andExpect(jsonPath("_links.update-event").exists())
                .andExpect(jsonPath("_links.profile").exists())
                .andDo(document("get-event"))
        ;
    }

    @Test
    @DisplayName("단건 조회 실패")
    public void getEventOne_Error() throws Exception {
        this.mockMvc.perform(post("/api/events/99"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("_links.index").exists())
                .andExpect(jsonPath("Error-Message").exists())
                .andDo(document("get-event-error"))
        ;
    }

    @Autowired
    private ModelMapper modelMapper;

    @Test
    @DisplayName("이벤트 수정")
    public void updateEvent() throws Exception {
        this.generateEvent();

        Event event = this.eventRepository.findById(1).orElseThrow(() -> new IllegalArgumentException("not found id - 1"));
        event.setName("update event name " + event.getId());
        event.setDescription("update event description " + event.getId());

        EventDto eventDto = modelMapper.map(event, EventDto.class);

        this.mockMvc.perform(
                        put("/api/events/" + event.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaTypes.HAL_JSON)
                                .content(objectMapper.writeValueAsString(eventDto))
                )
                .andDo(print())
                .andExpect(status().isOk())
        ;
    }

}