package kafka.demo.demo.controllers;


import kafka.demo.demo.model.ListenerDto;
import kafka.demo.demo.services.consumers.KafkaListenerContainerManagerServiceImpl;
import kafka.demo.demo.services.producers.IProducerService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class ControllerDemo implements IController{

    private final IProducerService producerService;
    private final KafkaListenerContainerManagerServiceImpl kafkaListenerContainerManager;
    private static final Logger logger = LoggerFactory.getLogger(ControllerDemo.class);

    /**
     * This method invokes the producer service, publishes 100 messages to topic
     */
    @Override
    public ResponseEntity<String> invokeProducer() {
        logger.info("Invoking producer");
        for (int times = 0; times < 100; times++) {
            final int key = times % 10;
            producerService.sendMessage(String.valueOf(key), UUID.randomUUID().toString());
        }
        logger.info("All Messages Published");
        return ResponseEntity.ok("Done");
    }


    /**
     * This method when invoked, creates and registers a listener which starts polling messages from topic
     *
     * @param listenerDto - dto object for carry listener information like topic name or isStartImmediately
     * @return uuid - listenerId
     */
    @Override
    public UUID createListener(final ListenerDto listenerDto) {
        logger.info("Invoking Consumer");

        final UUID listenerId = UUID.randomUUID();
        kafkaListenerContainerManager.registerListener(
                listenerId.toString(),
                listenerDto.topic(),
                listenerDto.isStartImmediately()
        );
        return listenerId;
    }
}
