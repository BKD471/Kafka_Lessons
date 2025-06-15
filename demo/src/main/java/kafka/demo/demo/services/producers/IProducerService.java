package kafka.demo.demo.services.producers;



public interface IProducerService {
    /**
     * This method publishes payload to topic
     *
     * @param key - key of payload
     * @param message - the message which sis published to topic
     * */
    void sendMessage(final String key,final String message);
}
