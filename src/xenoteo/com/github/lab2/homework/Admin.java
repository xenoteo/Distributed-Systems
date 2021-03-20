package xenoteo.com.github.lab2.homework;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

public class Admin {
    public static final String ADMIN_EXCHANGE_NAME = "admin_exchange";

    public static final String ADMIN_IN_QUEUE = "admin";

    public static final String ADMIN_IN_KEY = "admin.in";
    public static final String ADMIN_OUT_KEY = "admin.out";
    public static final String ADMIN_SUPPLIERS_KEY = "admin.suppliers";
    public static final String ADMIN_SQUADS_KEY = "admin.squads";


    public static void main(String[] args) {
        Admin admin = new Admin();
        admin.run();
    }

    private void run(){
        System.out.println("ADMIN");

        try{
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("localhost");
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();

            channel.exchangeDeclare(ADMIN_EXCHANGE_NAME, BuiltinExchangeType.TOPIC);

            channel.queueDeclare(ADMIN_IN_QUEUE, false, false, false, null);
            channel.basicConsume(ADMIN_IN_QUEUE, true, createAdminConsumer(channel));

            Scanner in = new Scanner(System.in);
            while (true) {
                System.out.println("Enter recipients (1 for squads, 2 for suppliers, 3 for everyone, q to quit):");
                String recipient = in.nextLine();
                if ("q".equals(recipient)) {
                    break;
                }

                String routingKey;
                switch (recipient){
                    case "1" -> routingKey = ADMIN_SQUADS_KEY;
                    case "2" -> routingKey = ADMIN_SUPPLIERS_KEY;
                    case "3" -> routingKey = ADMIN_OUT_KEY;
                    default -> {
                        System.out.println("Please enter correct recipient");
                        continue;
                    }
                }

                System.out.println("Enter the message:");
                String message = in.nextLine();

                channel.basicPublish(ADMIN_EXCHANGE_NAME, routingKey, null, message.getBytes(StandardCharsets.UTF_8));
            }

            channel.close();
            connection.close();
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }

    }

    private Consumer createAdminConsumer(Channel channel){
        return new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) {
                String message = new String(body, StandardCharsets.UTF_8);
                String confirmation = String.format("RECEIVED: %s", message);
                ColoredOutput.printlnGreen(confirmation);
            }
        };
    }
}
