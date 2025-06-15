package kafka.demo.demo.model;


import lombok.NonNull;
import org.springframework.validation.annotation.Validated;

@Validated
public record ListenerDto(
        @NonNull
        String topic,
        boolean isStartImmediately
) {
}
