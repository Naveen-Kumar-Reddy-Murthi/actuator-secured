package com.example.actuatorsecured;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import java.net.Socket;

@Slf4j
@Component
public class UPStreamServiceHealthIndicator implements HealthIndicator {
    private final String message_key = "Clean URI Service";
    private static final String URL = "http://localhost.com";
    @Override
    public Health health() {
        String errorMessage = null;
        if (!isRunningServiceB(errorMessage)) {
            return Health.down().withDetail(message_key, "Not Available - "+errorMessage).build();
        }
        return Health.up().withDetail(message_key, "Available").build();
    }
    private Boolean isRunningServiceB(String errorMessage) {
        Boolean isRunning = true;
        // check if url shortener service url is reachable
        try (Socket socket =
                     new Socket(new java.net.URL(URL).getHost(),8081)) {
        } catch (Exception e) {
            log.warn("Failed to connect to: {}",URL);
//            return Health.down()
//                    .withDetail("error", e.getMessage())
//                    .build();
            errorMessage = e.getMessage();
            isRunning = false;
        }
        return isRunning;
    }
}
