package xenoteo.com.github.lab2.homework;

import com.rabbitmq.client.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicLong;

public class Supplier {
    public static int id = 0;
    public static void main(String[] argv) {
        AtomicLong lastCommissionNumber = new AtomicLong(0);

        id++;
        System.out.printf("SUPPLIER %d\n", id);

        Set<String> equipment = readEquipment();

        try{
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("localhost");
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            channel.basicQos(1);

            String EXCHANGE_NAME = "tourism";
            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);

            assert equipment != null;
            for (String item : equipment){
                channel.queueDeclare(item, false, false, false, null);
            }

            Consumer consumer = new DefaultConsumer(channel) {

                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) {
                    try{
                        String message = new String(body, StandardCharsets.UTF_8);
                        String item = envelope.getRoutingKey();
                        System.out.printf("Received request for %s from %s\n", item, message);

                        long commissionNumber = lastCommissionNumber.incrementAndGet();
                        String confirmation = String.format("%s (commission #%d)", item, commissionNumber);

                        channel.queueDeclare(message, false, false, false, null);
                        channel.basicPublish("", message, null, confirmation.getBytes(StandardCharsets.UTF_8));

                        channel.basicAck(envelope.getDeliveryTag(), false);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            };

            for (String queueName : equipment){
                channel.basicConsume(queueName, false, consumer);
            }
        } catch (TimeoutException | IOException e) {
            e.printStackTrace();
        }

    }

    private static Set<String> readEquipment(){
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            Set<String> equipment = new HashSet<>();
            System.out.println("Enter equipment supplied (s to stop):");
            String item = br.readLine().toLowerCase();
            while (!item.equals("s")){
                if (!item.isEmpty())
                    equipment.add(item);
                item = br.readLine().toLowerCase();
            }
            System.out.printf("Supplying the following equipment: %s\n", equipment.toString());
            return equipment;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
