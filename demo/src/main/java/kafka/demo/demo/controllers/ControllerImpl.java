package kafka.demo.demo.controllers;


import kafka.demo.demo.dto.ListenerDTO;
import kafka.demo.demo.services.consumers.IKafkaListenerContainerManagerService;
import kafka.demo.demo.services.producers.IProducerService;
import kafka.demo.demo.utils.ApplicationProperties;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequiredArgsConstructor
public class ControllerImpl implements IControllerService {

    private static final Logger logger = LoggerFactory.getLogger(ControllerImpl.class);

    private final IProducerService producerService;
    private final IKafkaListenerContainerManagerService kafkaListenerContainerManagerService;
    private final ApplicationProperties applicationProperties;
    private final AtomicLong id = new AtomicLong(0);


    /**
     * This method invokes the producer service, publishes 10000 messages to topic
     *
     * @return ResponseEntity<String> - acknowledgement of send with status code
     */
    @Override
    public ResponseEntity<String> invokeProducer() {
        logger.info("Invoking producer");
        final int total_partitions = applicationProperties.partitions();
        for (int times = 0; times < 10000; times++) {
            final String key = String.valueOf(times % total_partitions);
            final String message = UUID.randomUUID().toString();
            producerService.sendMessage(key, message);
        }
        return new ResponseEntity<>(
                "Done with Publishing",
                HttpStatus.ACCEPTED
        );
    }


    /**
     * This method when invoked, registers a listener which starts polling messages from topic
     *
     * @param listenerDto - dto object for listener information like topic name or isStartImmediately
     * @return ResponseEntity<String> - listenerId with status code
     */
    @Override
    public ResponseEntity<String> createListener(final ListenerDTO listenerDto) {
        logger.info("Invoking Consumer");

        final String listenerId = generateListenerId();
        kafkaListenerContainerManagerService.registerListener(
                listenerId,
                listenerDto.topic(),
                listenerDto.isStartImmediately()
        );
        return new ResponseEntity<>(
                String.format("Listener Created with id: %s", listenerId),
                HttpStatus.ACCEPTED
        );
    }

    private String generateListenerId() {
        return "kafkaListenerId-" + id.getAndIncrement();
    }
}
