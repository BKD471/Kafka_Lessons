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

        @NonNull
        String compressionType,

        boolean isUncleanElection,

        @NonNull
        String ackConfig,

        @NonNull
        String groupId,

        @NonNull
        String offSetReset,

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
