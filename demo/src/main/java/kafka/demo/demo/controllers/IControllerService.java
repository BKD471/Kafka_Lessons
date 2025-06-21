package kafka.demo.demo.controllers;


import jakarta.validation.Valid;
import kafka.demo.demo.model.ListenerDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
     * This method when invoked, creates and registers a listener which starts polling messages from topic
     *
     * @param listenerDto - dto object for carry listener information like topic name or isStartImmediately
     * @return ResponseEntity<String> - listenerId with status code
     */
    @PostMapping("/v1/consume")
    ResponseEntity<String> createListener(@Valid @RequestBody final ListenerDTO listenerDto);
}
