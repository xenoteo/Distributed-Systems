package xenoteo.com.github.lab2.homework;

import com.rabbitmq.client.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class Squad {
    public static void main(String[] argv) throws IOException, TimeoutException {
        try{
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            System.out.print("Enter the squad name: ");
            String squadName = br.readLine();
            squadName = squadName.toUpperCase();
            System.out.printf("SQUAD %s\n", squadName);

            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("localhost");
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();

            String EXCHANGE_NAME = "tourism";
            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);

            channel.queueDeclare(squadName, false, false, false, null);

            Consumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) {
                    String message = new String(body, StandardCharsets.UTF_8);
                    String confirmation = String.format("Received %s", message);
                    ColoredOutput.printlnGreen(confirmation);
                }
            };

            channel.basicConsume(squadName, true, consumer);

            while (true) {
                System.out.println("Enter item to supply (q to quit):");
                String item = br.readLine();

                if ("q".equals(item)) {
                    break;
                }

                item = item.toLowerCase();
                channel.queueDeclare(item, false, false, false, null);

                channel.basicPublish("", item, null, squadName.getBytes(StandardCharsets.UTF_8));
                System.out.printf("Sent request for %s\n", item);
            }

            channel.close();
            connection.close();

        } catch (TimeoutException | IOException e) {
            e.printStackTrace();
        }
    }
}
