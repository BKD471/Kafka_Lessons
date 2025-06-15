package kafka.demo.demo.services.consumers;

public interface IKafkaListenerContainerManagerService {

    /**
     * This method creates and registers listeners to the topic,
     * we use it to poll messages from topic on demand dynamically, you can try using KafkaListener as alternative
     *
     * @param listenerId - listener id
     * @param topic      - topic name from which we want to poll
     * @param startImmediately - should the container start immediately after registration
     * */
    void registerListener(final String listenerId, final String topic, final boolean startImmediately);
}
