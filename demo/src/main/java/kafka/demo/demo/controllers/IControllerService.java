package kafka.demo.demo.controllers;


import jakarta.validation.Valid;
import kafka.demo.demo.dto.ListenerDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RequestMapping("/api/invoke")
public interface IControllerService {
    /**
     * This method invokes the producer service, publishes 10000 messages to topic
     *
     * @return ResponseEntity<String> - acknowledgement of send with status code
     */
    @PostMapping("/v1/publishAll")
    ResponseEntity<String> invokeProducer();

    /**
     * This method when invoked, registers a listener which starts polling messages from topic
     *
     * @param listenerDto - dto object for listener information like topic name or isStartImmediately
     * @return ResponseEntity<String> - listenerId with status code
     */
    @PostMapping("/v1/consume")
    ResponseEntity<String> createListener(@Valid @RequestBody final ListenerDTO listenerDto);
}
