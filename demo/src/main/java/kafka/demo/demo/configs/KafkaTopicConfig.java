package kafka.demo.demo.configs;

import kafka.demo.demo.utils.ApplicationProperties;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.config.TopicConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.AbstractMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class KafkaTopicConfig {

    private final ApplicationProperties applicationProperties;

    @Bean
    public KafkaAdmin kafkaAdmin() {
        final Map<String, Object> configsMap = Map.ofEntries(
                new AbstractMap.SimpleEntry<>
                        (
                                AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG,
                                applicationProperties.bootstrapServers()
                        ),
                new AbstractMap.SimpleEntry<>
                        (
                                AdminClientConfig.RETRY_BACKOFF_MS_CONFIG,
                                applicationProperties.retryBackOffMs()
                        ),
                new AbstractMap.SimpleEntry<>
                        (
                                AdminClientConfig.REQUEST_TIMEOUT_MS_CONFIG,
                                applicationProperties.requestTimeOutMs()
                        )
        );
        return new KafkaAdmin(configsMap);
    }

    @Bean
    public NewTopic topic() {
        return TopicBuilder
                .name(applicationProperties.topicName())
                .configs(constructTopicConfigsMap())
                .partitions(applicationProperties.partitions())
                .replicas(applicationProperties.replicationFactor())
                .build();
    }

    private Map<String, String> constructTopicConfigsMap() {
        return Map.ofEntries(
                new AbstractMap.SimpleEntry<>
                        (
                                TopicConfig.MIN_IN_SYNC_REPLICAS_CONFIG,
                                String.valueOf(applicationProperties.minInSyncReplicas())
                        ),
                new AbstractMap.SimpleEntry<>
                        (
                                TopicConfig.UNCLEAN_LEADER_ELECTION_ENABLE_CONFIG,
                                String.valueOf(applicationProperties.isUncleanElection())
                        )
        );
    }
}
