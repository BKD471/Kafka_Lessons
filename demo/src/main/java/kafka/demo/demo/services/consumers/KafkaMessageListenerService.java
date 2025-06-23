package kafka.demo.demo.services.consumers;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.stereotype.Service;


@Service
public class KafkaMessageListenerService implements MessageListener<String, String> {

    private static final Logger log = LoggerFactory.getLogger(KafkaMessageListenerService.class);

    /**
     * This method polls messages from topic and logs its detailed info.
     *
     * @param record - contains info about topic,offset,key,value,partition
     */
    @Override
    public void onMessage(final ConsumerRecord<String, String> record) {
        try {
            Thread.sleep(4000); // consumer logs in every 4 seconds
            log.info("Polled new message:  key: {}, value: {}  from topic: {} " +
                            "partition: {} with offset: {}",
                    record.key(), record.value(), record.topic(),
                    record.partition(), record.offset());
        } catch (Exception e) {
            log.error(" Error while processing record: ", e);
            // if processing fails, configure a DLQ here to re-process again.
        }
    }
}