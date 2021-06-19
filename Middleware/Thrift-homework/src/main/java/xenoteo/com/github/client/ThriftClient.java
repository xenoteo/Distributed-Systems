package xenoteo.com.github.client;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.*;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import xenoteo.com.github.thrift.gen.Transfer;

import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/**
 * The simple Thrift client sending data of different size,
 * stored in different datastructures, using different Thrift serialisation methods
 * to the Thrift simple server.
 */
public class ThriftClient {

    public static void main(String[] args) {
        String host = "127.0.0.2";
        TTransport transport = new TSocket(host, 9080);

        if (args.length < 1) {
            printError();
            System.exit(1);
        }

        TProtocol protocol = readProtocol(args[0], transport);
        if (protocol == null) {
            printError();
            System.exit(1);
        }

        Transfer.Client client = new Transfer.Client(protocol);

        try {
            boolean running = true;
            Scanner in = new Scanner(System.in);
            DataGenerator dataGenerator = new DataGenerator();
            transport.open();
            while (running) {
                System.out.print("Choose a data structure to transfer (list, set or map; q to quit): ");
                String choice = in.nextLine();
                if (choice.equals("list") || choice.equals("set") || choice.equals("map")) {
                    System.out.print("Choose a data structure size: ");
                    int size = in.nextInt();
                    in.nextLine();
                    long sendTime = 1;
                    long gotTime = 0;
                    switch (choice){
                        case "list" -> {
                            List<Integer> data = dataGenerator.generateList(size);
                            sendTime = System.currentTimeMillis();
                            gotTime = client.transferList(data);
                        }
                        case "set" -> {
                            Set<Integer> data = dataGenerator.generateSet(size);
                            sendTime = System.currentTimeMillis();
                            gotTime = client.transferSet(data);
                        }
                        case "map" -> {
                            Map<Integer, Integer> data = dataGenerator.generateMap(size);
                            sendTime = System.currentTimeMillis();
                            gotTime = client.transferMap(data);
                        }
                    }
                    System.out.printf("%s of %d elements transfer time is %d ms\n", choice, size, gotTime - sendTime);
                }
                else if (choice.equals("q")){
                    running = false;
                }
            }
            transport.close();
        } catch (TException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Prints an error when bad arguments provided.
     */
    private static void printError() {
        System.err.println("Bad arguments provided.");
        System.err.println("The client needs a serialisation method to be provided as program argument: " +
                "binary | json | compact");
    }

    /**
     * Returns the TProtocol based on an argument provided.
     *
     * @param protocol  the string representing a protocol type
     * @param transport  the transport
     * @return the TProtocol of the required type
     */
    private static TProtocol readProtocol(String protocol, TTransport transport){
        return switch (protocol) {
            case "binary" -> new TBinaryProtocol(transport);
            case "json" -> new TJSONProtocol(transport);
            case "compact" -> new TCompactProtocol(transport);
            default -> null;
        };
    }
}
