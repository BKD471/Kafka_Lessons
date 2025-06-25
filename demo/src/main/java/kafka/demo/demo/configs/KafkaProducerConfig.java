package kafka.demo.demo.configs;


import kafka.demo.demo.utils.ApplicationProperties;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.AbstractMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class KafkaProducerConfig {

    private final ApplicationProperties applicationProperties;

    @Bean
    public ProducerFactory<String, String> producerFactory() {
        return new DefaultKafkaProducerFactory<>(constructConfigPropsMap());
    }

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    private Map<String, Object> constructConfigPropsMap() {
        return Map.ofEntries(
                new AbstractMap.SimpleEntry<>
                        (
                                ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                                applicationProperties.bootstrapServers()
                        ),
                new AbstractMap.SimpleEntry<>
                        (
                                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                                applicationProperties.keySerializer().getName()
                        ),
                new AbstractMap.SimpleEntry<>
                        (
                                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                                applicationProperties.valueSerializer().getName()
                        ),
                new AbstractMap.SimpleEntry<>
                        (
                                ProducerConfig.COMPRESSION_TYPE_CONFIG,
                                applicationProperties.compressionType()
                        ),
                new AbstractMap.SimpleEntry<>
                        (
                                ProducerConfig.ACKS_CONFIG,
                                applicationProperties.ackConfig()
                        ),
                new AbstractMap.SimpleEntry<>
                        (
                                AdminClientConfig.RETRIES_CONFIG,
                                applicationProperties.retries()
                        )
        );
    }
}
