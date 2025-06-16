package kafka.demo.demo.configs;

import kafka.demo.demo.utils.ApplicationConstants;
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

    private final ApplicationConstants applicationConstants;

    @Bean
    public KafkaAdmin kafkaAdmin() {
        final Map<String, Object> configsMap = Map.ofEntries(
                new AbstractMap.SimpleEntry<>
                        (AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, applicationConstants.bootstrapServers()),
                new AbstractMap.SimpleEntry<>
                        (AdminClientConfig.RETRIES_CONFIG, applicationConstants.retries()),
                new AbstractMap.SimpleEntry<>
                        (AdminClientConfig.RETRY_BACKOFF_MS_CONFIG, applicationConstants.retryBackOffMs()),
                new AbstractMap.SimpleEntry<>
                        (AdminClientConfig.REQUEST_TIMEOUT_MS_CONFIG, applicationConstants.requestTimeOutMs())
        );

        return new KafkaAdmin(configsMap);
    }

    @Bean
    public NewTopic topic() {
        return TopicBuilder
                .name(applicationConstants.topicName())
                .configs(constructTopicConfigsMap())
                .partitions(applicationConstants.partitions())
                .replicas(applicationConstants.replicationFactor())
                .build();
    }

    private Map<String,String> constructTopicConfigsMap(){
        return Map.ofEntries(
                new AbstractMap.SimpleEntry<>
                        (TopicConfig.MIN_IN_SYNC_REPLICAS_CONFIG, String.valueOf(applicationConstants.minInSyncReplicas())),
                new AbstractMap.SimpleEntry<>
                        (TopicConfig.UNCLEAN_LEADER_ELECTION_ENABLE_CONFIG, String.valueOf(applicationConstants.isUncleanElection()))
        );
    }
}
