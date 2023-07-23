package com.example.restapi;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.ArrayList;
import java.util.List;

import static com.example.restapi.ConcurrencySupport.*;

@ExtendWith(TimingExtension.class)
public class ConcurrencyTest {

    @Test
    @DisplayName("Multi Thread 미적용")
    public void shouldBeNotConcurrent() {
        start();

        for (int user = 1; user <= USERS; user++) {
            String serviceA = serviceA(user);
            String serviceB = serviceB(user);
            for (int i = 1; i <= PERSISTENCE_FORK_FACTOR; i++) {
                persistence(i, serviceA, serviceB);
            }
        }

        stop(
                1,
                USERS * (SERVICE_A_LATENCY + SERVICE_B_LATENCY + PERSISTENCE_LATENCY * PERSISTENCE_FORK_FACTOR)
        );
    }

    @Test
    @DisplayName("멀티 스레드 적용")
    public void shouldExecuteIterationsConcurrently() throws InterruptedException {
        List<Thread> threads = new ArrayList<>();
        for (int user = 1; user <= USERS; user++) {
            Thread thread = new Thread(new UserFlow(user));
            thread.start();
            threads.add(thread);
        }

        // Stop Condition - Not the most optimal but gets the work done
        for (Thread thread : threads) {
            thread.join();
        }
    }

    static class UserFlow implements Runnable {

        private final int user;

        UserFlow(int user) {
            this.user = user;
        }

        @Override
        public void run() {
            String serviceA = serviceA(user);
            String serviceB = serviceB(user);
            for (int i = 1; i <= PERSISTENCE_FORK_FACTOR; i++) {
                persistence(i, serviceA, serviceB);
            }
        }
    }

}
