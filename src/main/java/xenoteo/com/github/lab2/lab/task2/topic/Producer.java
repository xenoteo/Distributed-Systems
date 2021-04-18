package xenoteo.com.github.lab2.lab.task2.topic;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Producer {

    public static void main(String[] argv) throws Exception {
        System.out.println("TASK2 PRODUCER TOPIC");

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        String EXCHANGE_NAME = "exchange3";
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);

        while (true) {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("\nEnter message (q to quit): ");
            String message = br.readLine();

            if ("q".equals(message)) {
                break;
            }

            System.out.print("Enter key: ");
            String routingKey = br.readLine();

            channel.basicPublish(EXCHANGE_NAME, routingKey, null, message.getBytes("UTF-8"));
            System.out.println("Sent: " + message);
        }

        channel.close();
        connection.close();
    }
}
