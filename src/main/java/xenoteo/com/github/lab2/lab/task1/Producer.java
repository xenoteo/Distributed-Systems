package xenoteo.com.github.lab2.lab.task1;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Producer {

    public static void main(String[] argv) throws Exception {
        System.out.println("TASK1 PRODUCER");

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        String QUEUE_NAME = "queue1";
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while (true){
            System.out.print("Enter the message (q to quit): ");
            String message = br.readLine();
            if (message.equals("q")){
                break;
            }

            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            System.out.println("Sent: " + message);
        }

        channel.close();
        connection.close();
    }
}
