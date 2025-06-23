package kafka.demo.demo.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.validation.annotation.Validated;

@Validated
public record ListenerDTO(
        @NotBlank
        @Size(min = 3, max = 255, message = " Min topic name length must be 3, max must be within 255")
        String topic,

        @NotNull(message = "Please provide values for isStartImmediately")
        Boolean isStartImmediately
) {
}
