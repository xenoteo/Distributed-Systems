package xenoteo.com.github.lab.task2.direct;

import com.rabbitmq.client.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class Consumer {

    public static void main(String[] argv) throws Exception {
        System.out.println("TASK2 CONSUMER DIRECT");

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        String EXCHANGE_NAME = "exchange2";
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);

        String queueName = channel.queueDeclare().getQueue();

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Enter key: ");
        String routingKey = br.readLine();

        channel.queueBind(queueName, EXCHANGE_NAME, routingKey);
        System.out.println("Created queue: " + queueName);

        com.rabbitmq.client.Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, StandardCharsets.UTF_8);
                System.out.println("Received: " + message);
            }
        };

        System.out.println("Waiting for messages...");
        channel.basicConsume(queueName, true, consumer);
    }
}
