package xenoteo.com.github.lab2.homework;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

/**
 * The class representing the squad.
 */
public class Squad {
    /**
     * The name of the squad.
     */
    private final String squadName;

    public Squad() {
        squadName = readSquadName();
    }

    public static void main(String[] argv) {
        Squad squad = new Squad();
        squad.run();
    }

    /**
     * Runs the process of sending requests to suppliers and receiving confirmation from them.
     */
    private void run(){
        System.out.printf("SQUAD %s\n", squadName);
        try {
            // setting up the connection & channel
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("localhost");
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();

            // setting up the basic exchange and the squad queue
            channel.exchangeDeclare(Supplier.EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
            channel.queueDeclare(squadName, false, false, false, null);

            // creating the consumer for receiving confirmations and starting receiving them
            Consumer consumer = createSquadConsumer(channel);
            channel.basicConsume(squadName, true, consumer);

            // setting up the admin exchange and declaring queue for sending messages to admin
            channel.exchangeDeclare(Admin.ADMIN_EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
            channel.queueDeclare(Admin.ADMIN_IN_QUEUE, false, false, false, null);

            // setting up the queue for receiving messages from admin
            String adminQueueName = channel.queueDeclare().getQueue();
            channel.queueBind(adminQueueName, Admin.ADMIN_EXCHANGE_NAME, Admin.ADMIN_OUT_KEY);
            channel.queueBind(adminQueueName, Admin.ADMIN_EXCHANGE_NAME, Admin.ADMIN_SQUADS_KEY);

            // creating admin consumer and starting receiving messages from admin
            Consumer adminConsumer = createAdminConsumer(channel);
            channel.basicConsume(adminQueueName, true, adminConsumer);

            Scanner in = new Scanner(System.in);
            while (true) {
                // reading items to supply from terminal
                System.out.println("Enter item to supply (q to quit):");
                String item = in.nextLine().toLowerCase();
                if ("q".equals(item)) {
                    break;
                }

                // creating the queue for sending the request and sending the request
                byte[] messageBytes = squadName.getBytes(StandardCharsets.UTF_8);
                channel.queueDeclare(item, false, false, false, null);
                channel.basicPublish("", item, null, messageBytes);

                // sending the same message to admin
                channel.basicPublish("", Admin.ADMIN_IN_QUEUE, null, messageBytes);

                System.out.printf("Sent request for %s\n", item);
            }

            channel.close();
            connection.close();
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads the name of the squad from terminal.
     *
     * @return the name of the squad
     */
    private String readSquadName(){
        Scanner in = new Scanner(System.in);
        System.out.print("Enter the squad name: ");
        return in.nextLine().toUpperCase();
    }

    /**
     * Creates a consumer for receiving confirmations from suppliers by the squad.
     *
     * @param channel  the channel
     * @return the squad consumer
     */
    private Consumer createSquadConsumer(Channel channel){
        return new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) {
                String message = new String(body, StandardCharsets.UTF_8);
                String confirmation = String.format("Received %s", message);
                ColoredOutput.printlnGreen(confirmation);
            }
        };
    }

    /**
     * Creates a consumer for receiving messages from admin.
     *
     * @param channel  the channel
     * @return the admin consumer
     */
    private Consumer createAdminConsumer(Channel channel){
        return new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) {
                String message = new String(body, StandardCharsets.UTF_8);
                ColoredOutput.printlnBlue(String.format("RECEIVED FROM ADMIN: %s", message));
            }
        };
    }
}
