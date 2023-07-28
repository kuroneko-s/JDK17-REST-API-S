package com.example.restapi.event.domain;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(JUnitParamsRunner.class)
class EventTest {
    @Test
    @DisplayName("")
    void test() {
        Event event = Event.builder().build();

        assertNotNull(event);
    }

    @Test
    @DisplayName("free 바인딩 테스트")
    @Parameters({
            "0, 0, true",
    })
    public void free_test1(int basePrice, int maxPrice, boolean result) {
        Event event = Event.builder()
                .basePrice(basePrice)
                .maxPrice(maxPrice)
                .build();

        event.update();

        assertEquals(event.isFree(), result);
    }
}