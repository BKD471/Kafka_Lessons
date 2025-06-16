package kafka.demo.demo.configs;


import kafka.demo.demo.utils.ApplicationConstants;
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

    private final ApplicationConstants applicationConstants;

    @Bean
    public ProducerFactory<String, String> producerFactory() {
        final Map<String, Object> producerConfigPropsMap = Map.ofEntries(
                new AbstractMap.SimpleEntry<>
                        (ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, applicationConstants.bootstrapServers()),
                new AbstractMap.SimpleEntry<>
                        (ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, applicationConstants.keySerializer().getName()),
                new AbstractMap.SimpleEntry<>
                        (ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, applicationConstants.valueSerializer().getName()),
                new AbstractMap.SimpleEntry<>
                        (ProducerConfig.COMPRESSION_TYPE_CONFIG, applicationConstants.compressionType()),
                new AbstractMap.SimpleEntry<>
                        (ProducerConfig.ACKS_CONFIG, applicationConstants.ackConfig()),
                new AbstractMap.SimpleEntry<>
                        (AdminClientConfig.RETRIES_CONFIG, applicationConstants.retries())
        );

        return new DefaultKafkaProducerFactory<>(producerConfigPropsMap);
    }

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}
