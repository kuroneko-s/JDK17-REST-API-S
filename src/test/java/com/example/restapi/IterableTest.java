package com.example.restapi;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.Observer;
import java.util.concurrent.Flow;
import java.util.stream.Stream;

public class IterableTest {

    @Test
    @DisplayName("Iterable 테스트")
    public void test() {
        Iterable<Integer> iter = () -> new Iterator<>() {
            private int start = 0;
            private final static int MAX = 10;

            @Override
            public boolean hasNext() {
                return start < MAX;
            }

            @Override
            public Integer next() {
                return ++start;
            }
        };

        for (Integer integer : iter) {
            System.out.println(integer);
        }

        // Observer
        // Flow.Subscriber
    }

}
