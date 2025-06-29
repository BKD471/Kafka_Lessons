package kafka.demo.demo.dto;

import java.time.LocalTime;

public record ErrorDetails(
        LocalTime timeStamp,
        String message,
        String details) {
}
