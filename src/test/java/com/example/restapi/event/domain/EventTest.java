package com.example.restapi.event.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EventTest {
    @Test
    @DisplayName("")
    void test() {
        Event event = Event.builder().build();

        assertNotNull(event);
    }
}