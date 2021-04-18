package xenoteo.com.github.lab.task1;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class Consumer {

    public static void main(String[] argv) throws Exception {
        System.out.println("TASK1 CONSUMER");

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        String QUEUE_NAME = "queue1";
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        channel.basicQos(1);

        com.rabbitmq.client.Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("Processing started");
                String message = new String(body, StandardCharsets.UTF_8);
                try {
                    int timeToSleep = Integer.parseInt(message);
                    Thread.sleep(timeToSleep * 1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Received: " + message);
                System.out.println("Processing finished");
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        };

        System.out.println("Waiting for messages...");
        channel.basicConsume(QUEUE_NAME, false, consumer);

//        channel.close();
//        connection.close();
    }
}
