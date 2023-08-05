package com.example.learing.concurrency;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.ArrayList;
import java.util.List;

/**
 * https://homoefficio.github.io/2020/12/11/Java-Concurrency-Evolution/
 */

@ExtendWith(TimingExtension.class)
public class ConcurrencyTest {

    @Test
    @DisplayName("Multi Thread 미적용")
    public void shouldBeNotConcurrent() {
        ConcurrencySupport.start();

        for (int user = 1; user <= ConcurrencySupport.USERS; user++) {
            String serviceA = ConcurrencySupport.serviceA(user);
            String serviceB = ConcurrencySupport.serviceB(user);
            for (int i = 1; i <= ConcurrencySupport.PERSISTENCE_FORK_FACTOR; i++) {
                ConcurrencySupport.persistence(i, serviceA, serviceB);
            }
        }

        ConcurrencySupport.stop(
                1,
                ConcurrencySupport.USERS * (ConcurrencySupport.SERVICE_A_LATENCY + ConcurrencySupport.SERVICE_B_LATENCY + ConcurrencySupport.PERSISTENCE_LATENCY * ConcurrencySupport.PERSISTENCE_FORK_FACTOR)
        );
    }

    @Test
    @DisplayName("멀티 스레드 적용")
    public void shouldExecuteIterationsConcurrently() throws InterruptedException {
        List<Thread> threads = new ArrayList<>();
        for (int user = 1; user <= ConcurrencySupport.USERS; user++) {
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
            String serviceA = ConcurrencySupport.serviceA(user);
            String serviceB = ConcurrencySupport.serviceB(user);
            for (int i = 1; i <= ConcurrencySupport.PERSISTENCE_FORK_FACTOR; i++) {
                ConcurrencySupport.persistence(i, serviceA, serviceB);
            }
        }
    }

}
