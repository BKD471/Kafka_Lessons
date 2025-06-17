package kafka.demo.demo.controllers;


import jakarta.validation.Valid;
import kafka.demo.demo.model.ListenerDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;


@RequestMapping("/api/v1/invoke")
public interface IControllerService {
    /**
     * This method invokes the producer service, publishes 10000 messages to topic
     * */
    @PostMapping("/publishAll")
    ResponseEntity<String> invokeProducer();

    /**
     * This method when invoked, creates and registers a listener which starts polling messages from topic
     * @param listenerDto - dto object for carry listener information like topic name or isStartImmediately
     * @return uuid - listenerId
     */
    @GetMapping("/consume")
    UUID createListener(@Valid @RequestBody final ListenerDto listenerDto);
}
