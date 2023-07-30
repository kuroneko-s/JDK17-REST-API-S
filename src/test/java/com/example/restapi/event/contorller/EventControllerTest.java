package com.example.restapi.event.contorller;

import com.example.restapi.RestDocsConfigure;
import com.example.restapi.event.domain.Event;
import com.example.restapi.event.domain.EventDto;
import com.example.restapi.event.domain.EventStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDateTime;

import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
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
class EventControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

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
                .andExpect(jsonPath("$[0].objectName").exists())
                .andExpect(jsonPath("$[0].code").exists())
                .andExpect(jsonPath("$[0].defaultMessage").exists())
                .andReturn();

        System.out.println(mvcResult);

    }

}