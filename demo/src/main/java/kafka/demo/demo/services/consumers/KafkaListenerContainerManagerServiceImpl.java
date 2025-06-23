package kafka.demo.demo.services.consumers;

import kafka.demo.demo.utils.ApplicationProperties;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerEndpoint;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.config.MethodKafkaListenerEndpoint;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class KafkaListenerContainerManagerServiceImpl implements IKafkaListenerContainerManagerService {

    private final KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry;
    private final KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> kafkaListenerContainerFactory;
    private final ApplicationProperties applicationProperties;

    /**
     * This method creates and registers listeners to the topic,
     * we use it to poll messages from topic on demand dynamically,
     * you can try using KafkaListener as alternative
     *
     * @param listenerId       - listener id
     * @param topic            - topic name from which we want to poll
     * @param startImmediately - should the container start immediately after registration
     */
    @SneakyThrows
    @Override
    public void registerListener(
            final String listenerId,
            final String topic,
            final boolean startImmediately
    ) {
        kafkaListenerEndpointRegistry.registerListenerContainer(
                createKafkaListenerEndpoint(listenerId, topic),
                kafkaListenerContainerFactory,
                startImmediately
        );
    }

    @SneakyThrows
    private KafkaListenerEndpoint createKafkaListenerEndpoint(
            final String listenerId,
            final String topic
    ) {
        MethodKafkaListenerEndpoint<String, String> kafkaListenerEndpoint =
                new MethodKafkaListenerEndpoint<>();
        kafkaListenerEndpoint.setId(listenerId);
        kafkaListenerEndpoint.setGroupId(applicationProperties.groupId());
        kafkaListenerEndpoint.setAutoStartup(true);
        kafkaListenerEndpoint.setTopics(topic);
        kafkaListenerEndpoint.setMessageHandlerMethodFactory(
                new DefaultMessageHandlerMethodFactory()
        );
        kafkaListenerEndpoint.setBean(new KafkaMessageListenerService());
        kafkaListenerEndpoint.setMethod(
                KafkaMessageListenerService.class.getMethod(
                        "onMessage",
                        ConsumerRecord.class
                )
        );
        return kafkaListenerEndpoint;
    }
}
