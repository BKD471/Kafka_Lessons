package kafka.demo.demo.configs;

import kafka.demo.demo.utils.ApplicationProperties;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import java.util.AbstractMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class KafkaConsumerConfig {

    private final ApplicationProperties applicationProperties;

    @Bean
    public ConsumerFactory<String, String> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(constructConsumerPropsMap());
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
        final ConcurrentKafkaListenerContainerFactory<String, String> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }

    private Map<String, Object> constructConsumerPropsMap() {
        return Map.ofEntries(
                new AbstractMap.SimpleEntry<>
                        (
                                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
                                applicationProperties.bootstrapServers()
                        ),
                new AbstractMap.SimpleEntry<>
                        (
                                ConsumerConfig.GROUP_ID_CONFIG,
                                applicationProperties.groupId()
                        ),
                new AbstractMap.SimpleEntry<>
                        (
                                ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG,
                                applicationProperties.sessionTimeOutMs()
                        ),
                new AbstractMap.SimpleEntry<>
                        (
                                ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,
                                applicationProperties.offSetReset()
                        ),
                new AbstractMap.SimpleEntry<>
                        (
                                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                                applicationProperties.keyDeSerializer().getName()
                        ),
                new AbstractMap.SimpleEntry<>
                        (
                                ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                                applicationProperties.valueDeSerializer().getName()
                        )
        );
    }
}
