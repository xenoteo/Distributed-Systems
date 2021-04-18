package xenoteo.com.github.lab2.homework;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.TimeoutException;

/**
 * The class representing the supplier.
 */
public class Supplier {
    /**
     * The exchange name.
     */
    public static final String EXCHANGE_NAME = "tourism";
    /**
     * The number of the last order handled by the supplier.
     */
    private long lastCommissionNumber;
    /**
     * The name of the supplier.
     */
    private final String supplierName;
    /**
     * The supplier prefix.
     */
    private final String supplierPrefix;

    public Supplier() {
        lastCommissionNumber = 0;
        supplierName = readSupplierName();
        supplierPrefix = supplierName
                .replaceAll("\\p{Punct}", "").replaceAll("\\s", "") + "-";
    }

    public static void main(String[] argv) {
        Supplier supplier = new Supplier();
        supplier.run();
    }

    /**
     * Runs the process of receiving requests from squads, handling requests and sending the confirmations to squads.
     */
    private void run(){
        System.out.printf("SUPPLIER %s\n", supplierName);
        try{
            // setting up the connection & channel
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("localhost");
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();

            // setting up the QoS
            channel.basicQos(1);

            // setting up the basic exchange
            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);

            // reading the equipment supplied by the supplier
            Set<String> equipment = readEquipment();

            // setting up the admin exchange and declaring queue for sending messages to admin
            channel.exchangeDeclare(Admin.ADMIN_EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
            channel.queueDeclare(Admin.ADMIN_IN_QUEUE, false, false, false, null);

            // setting up the queue for receiving messages from admin
            String adminQueueName = channel.queueDeclare().getQueue();
            channel.queueBind(adminQueueName, Admin.ADMIN_EXCHANGE_NAME, Admin.ADMIN_OUT_KEY);
            channel.queueBind(adminQueueName, Admin.ADMIN_EXCHANGE_NAME, Admin.ADMIN_SUPPLIERS_KEY);

            // creating admin consumer and starting receiving messages from admin
            Consumer adminConsumer = createAdminConsumer(channel);
            channel.basicConsume(adminQueueName, true, adminConsumer);

            // creating consumer for receiving and handling requests from squads and sending the confirmations
            // and start receiving requests on each equipment item's channel
            Consumer consumer = createSupplierConsumer(channel);
            for (String queueName : equipment){
                channel.queueDeclare(queueName, false, false, false, null);
                channel.basicConsume(queueName, false, consumer);
            }

        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads the name of the supplier from terminal.
     *
     * @return the name of the supplier
     */
    private String readSupplierName(){
        Scanner in = new Scanner(System.in);
        System.out.print("Enter the supplier name: ");
        return in.nextLine().toUpperCase();
    }

    /**
     * Creates a consumer for receiving requests from squads, handling requests and sending the confirmations to squads.
     *
     * @param channel  the channel
     * @return the supplier consumer
     */
    private Consumer createSupplierConsumer(Channel channel){
        return new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) {
                try{
                    // getting the name of the squad and the item needed
                    String squad = new String(body, StandardCharsets.UTF_8);
                    String item = envelope.getRoutingKey();
                    System.out.printf("Received request for %s from %s\n", item, squad);

                    // creating the confirmation
                    String commissionNumber = supplierPrefix + (++lastCommissionNumber);
                    String confirmation = String.format("%s (commission %s)", item, commissionNumber);

                    // creating the queue for sending the confirmation to the squad and sending it
                    byte[] messageBytes = confirmation.getBytes(StandardCharsets.UTF_8);
                    channel.queueDeclare(squad, false, false, false, null);
                    channel.basicPublish("", squad, null, messageBytes);

                    // sending the same message to admin
                    channel.basicPublish("", Admin.ADMIN_IN_QUEUE, null, messageBytes);

                    // confirming the request
                    channel.basicAck(envelope.getDeliveryTag(), false);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        };
    }

    /**
     * Reads the equipment supplied by the supplier.
     *
     * @return the equipment supplied by the supplier
     */
    private Set<String> readEquipment(){
        Scanner in = new Scanner(System.in);
        Set<String> equipment = new HashSet<>();

        System.out.println("Enter equipment supplied (s to stop):");

        String item = in.nextLine().toLowerCase();
        while (!item.equals("s")){
            if (!item.isEmpty())
                equipment.add(item);
            item = in.nextLine().toLowerCase();
        }

        System.out.printf("Supplying the following equipment: %s\n", equipment.toString());
        return equipment;
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
