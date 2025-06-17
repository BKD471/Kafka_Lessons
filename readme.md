Hello World, I Created this repo just to give practical demonstration on how we use Kafka in Industries.

To follow this lesson make sure you follow this steps

1) Make Sure you have Docker Desktop & JDK 21 Installed on your machine.
2) Get the docker compose file from this repo (I have shared) & set up a multi broker cluster in yr machine.
   Run the command "docker compose up -d" in the same dir as this docker compose file 
   It will look like this

   ![img.png](setup_static/img.png)


3) Clone my repo & do a simple maven import
   ![img_1.png](not_enough_replica_static/img_1.png)

You are now good to follow along!!!

# **Question was we have 3 replicas with min_in_sync_replicas=2 & ack=all right?**

so let's configure them one by one

![img_2.png](not_enough_replica_static/img_2.png)

![img_9.png](not_enough_replica_static/img_9.png)

![img_4.png](not_enough_replica_static/img_4.png)

**This is Kafka UI, very helpful for monitoring the cluster**

![img_5.png](not_enough_replica_static/img_5.png)

![img_6.png](not_enough_replica_static/img_6.png)

![img_7.png](not_enough_replica_static/img_7.png)

Do not forget to set retries to 3 or some lower numbers, else it will keep on retrying for 2147483647 
until delivery.timeout.ms expires, and it gives TimeoutException.

![img_23.png](not_enough_replica_static/img_23.png)

Let's come to the coding part, In controller we have only two apis for simplicity(for this demo only)

1) for producing messages to topic - upon invocation, it produces 100 messages to topic. 

2) for polling messages from topic - upon invocation, it creates a listener which keeps polling in every 4 seconds

![img_20.png](not_enough_replica_static/img_20.png)


# **Scenario 1: We have all 3 brokers/replicas are live, min_insync=2, ack=all & lets produce & consume**

![img_11.png](not_enough_replica_static/img_11.png)

lets produce
![img_12.png](not_enough_replica_static/img_12.png)

messages got published
![img_13.png](not_enough_replica_static/img_13.png)

lets start the consumer
![img_14.png](not_enough_replica_static/img_14.png)
messages are being polled
![img_19.png](not_enough_replica_static/img_19.png)

# **Conclusion 1: Both Producers & Consumers are working smoothly.**


# **Scenario 2: We stopped/downed  2 brokers/replicas, min_in_sync=2, ack=all & lets produce & consume**


![img_16.png](not_enough_replica_static/img_16.png)

triggered the publisher api from postman and got NotEnoughReplicasException 
![img_17.png](not_enough_replica_static/img_17.png)

triggered the consumer/listener api from postman and we are polling smoothly
![img_21.png](not_enough_replica_static/img_21.png)


# **Conclusion 2 : Producers throws NotEnoughReplicasException but Consumer is working smoothly.**


















