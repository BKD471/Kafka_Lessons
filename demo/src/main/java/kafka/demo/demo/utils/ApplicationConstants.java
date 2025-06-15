package kafka.demo.demo.utils;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.NonNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
@ConfigurationProperties(prefix = "kafka.configurations")
public record ApplicationConstants(
        @NonNull
        List<String> bootstrapServers,

        @NonNull
        String topicName,

        @Min(2)
        @Max(1000)
        int partitions,

        @Min(2)
        @Max(31)
        int replication_factor,

        @Min(1)
        @Max(31)
        int min_in_sync_replicas,

        @Min(1)
        @Max(5)
        int retries,

        @Min(100)
        @Max(1500)
        int retry_backoff_ms,

        @Min(10_000)
        @Max(1_00_000)
        int request_timeout_ms,

        @Min(10_000)
        @Max(1_000_000)
        int session_timeout_ms,

        @NonNull
        String compressionType,

        boolean isUncleanElection,

        @NonNull
        String ackConfig,

        @NonNull
        String groupId,

        @NonNull
        Class<?> keySerializer,

        @NonNull
        Class<?> valueSerializer,

        @NonNull
        Class<?> keyDeSerializer,

        @NonNull
        Class<?> valueDeSerializer
){
}
