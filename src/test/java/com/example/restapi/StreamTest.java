package com.example.restapi;

import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

public class StreamTest {

    @Test
    public void stream_test() {
        Stream.empty()
                .parallel();
        // parallel 에서의 병렬 동작은 Fork Join Pool을 사용함.
    }


}
