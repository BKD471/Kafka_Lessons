package kafka.demo.demo.model;


import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

@Validated
public record ListenerDto(
        @NotNull
        String topic,

        @NotNull
        boolean isStartImmediately
) {
}
