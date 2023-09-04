package com.example.restapi.event.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class EventTest {
    @Test
    @DisplayName("")
    void test() {
        Event event = Event.builder().build();

        assertNotNull(event);
    }

    @Test
    @DisplayName("free 바인딩 테스트")
    public void free_test1() {
        Event event = Event.builder()
                .basePrice(0)
                .maxPrice(0)
                .build();

        event.update();

        assertEquals(event.isFree(), true);
    }
}