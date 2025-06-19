package kafka.demo.demo.configs;

import kafka.demo.demo.utils.ApplicationConstants;
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

    private final ApplicationConstants applicationConstants;

    @Bean
    public ConsumerFactory<String, String> consumerFactory() {
        final Map<String, Object> consumerPropsMap = Map.ofEntries(
                new AbstractMap.SimpleEntry<>
                        (ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, applicationConstants.bootstrapServers()),
                new AbstractMap.SimpleEntry<>
                        (ConsumerConfig.GROUP_ID_CONFIG, applicationConstants.groupId()),
                new AbstractMap.SimpleEntry<>
                        (ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, applicationConstants.sessionTimeOutMs()),
                new AbstractMap.SimpleEntry<>
                        (ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, applicationConstants.offSetReset()),
                new AbstractMap.SimpleEntry<>
                        (ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, applicationConstants.keyDeSerializer().getName()),
                new AbstractMap.SimpleEntry<>
                        (ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, applicationConstants.valueDeSerializer().getName())
        );

        return new DefaultKafkaConsumerFactory<>(consumerPropsMap);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
        final ConcurrentKafkaListenerContainerFactory<String, String> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }
}
