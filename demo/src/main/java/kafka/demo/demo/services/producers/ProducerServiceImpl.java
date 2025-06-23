package kafka.demo.demo.services.producers;


import kafka.demo.demo.utils.ApplicationProperties;
import lombok.RequiredArgsConstructor;


import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;


import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ProducerServiceImpl implements IProducerService {

    private static final Logger logger = LoggerFactory.getLogger(ProducerServiceImpl.class);

    private final KafkaTemplate<String, String> kafkaProducerTemplate;
    private final ApplicationProperties applicationProperties;

    /**
     * This method publishes payload to topic
     *
     * @param key     - the key of message, it guides which partition the message is written to within a topic
     * @param message - the message which sis published to topic
     */
    @Override
    public void sendMessage(final String key, final String message) {
        final CompletableFuture<SendResult<String, String>> completableFuture =
                kafkaProducerTemplate.send(
                        applicationProperties.topicName(),
                        key,
                        message
                );

        completableFuture.whenComplete((record, exception) -> {
            if (null == exception) {
                final RecordMetadata recordMetadata = record.getRecordMetadata();
                logger.info("Received new metadata \nTopic: {} \nPartition: " +
                                "{} \noffset: {} \nTimeStamp: {} \n",
                        recordMetadata.topic(), recordMetadata.partition(),
                        recordMetadata.offset(), recordMetadata.timestamp());
            } else {
                logger.info(" Error while producing : {}", exception.getMessage());
                // if producing to topic fails, configure a DLQ here to retry again.
            }
        });
    }
}
