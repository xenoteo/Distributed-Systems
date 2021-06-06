package xenoteo.com.github.client;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TJSONProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import xenoteo.com.github.thrift.gen.Transfer;

import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class ThriftClient {

    public static void main(String[] args) {
        String host = "127.0.0.2";
        TTransport transport = new TSocket(host, 9080);
        TProtocol protocol = new TBinaryProtocol(transport);
//        TProtocol protocol = new TJSONProtocol(transport);
//        TProtocol protocol = new TCompactProtocol(transport);
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
}
