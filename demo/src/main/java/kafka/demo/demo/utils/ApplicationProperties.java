package kafka.demo.demo.utils;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
@ConfigurationProperties(prefix = "kafka.configurations")
public record ApplicationProperties(
        @NotEmpty
        List<String> bootstrapServers,

        @NotBlank
        @Size(min = 3, max = 255, message = " Min topic name length must be 3, max must be within 255")
        String topicName,

        @Min(1)
        @Max(1000)
        int partitions,

        @Min(3)
        @Max(31)
        int replicationFactor,

        @Min(1)
        @Max(31)
        int minInSyncReplicas,

        @Min(1)
        @Max(Integer.MAX_VALUE)
        int retries,

        @Min(100)
        @Max(1500)
        int retryBackOffMs,

        @Min(10_000)
        @Max(1_00_000)
        int requestTimeOutMs,

        @Min(10_000)
        @Max(1_000_000)
        int sessionTimeOutMs,

        @Pattern(regexp = "^(gzip|snappy|lz4|zstd|none)$",
                message = "valid values are gzip, snappy, lz4, zstd or give none for default")
        String compressionType,

        @NotNull
        Boolean isUncleanElection,

        @Pattern(regexp = "^(1|0|-1|all)$",
                message = "valid values are 1, 0, -1 or all")
        String ackConfig,

        @NotBlank
        String groupId,

        @NotBlank
        String offSetReset,

        @NotNull
        Class<?> keySerializer,

        @NotNull
        Class<?> valueSerializer,

        @NotNull
        Class<?> keyDeSerializer,

        @NotNull
        Class<?> valueDeSerializer
){
}
