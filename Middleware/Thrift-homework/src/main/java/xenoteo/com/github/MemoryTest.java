package xenoteo.com.github;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TJSONProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TMemoryBuffer;
import xenoteo.com.github.client.DataGenerator;

import java.util.List;

/**
 * A simple class running memory test, that is counting how many bytes are written to TMemoryBuffer
 * using different serialisation methods while sending 1000000 elements.
 */
public class MemoryTest {

    public static void main(String[] args) {
        if (args.length < 1) {
            printError();
            System.exit(1);
        }
        try {
            String protocolType = args[0];

            TMemoryBuffer trans = new TMemoryBuffer(4096);

            TProtocol protocol = readProtocol(protocolType, trans);
            if (protocol == null) {
                printError();
                System.exit(1);
            }

            DataGenerator generator = new DataGenerator();
            List<Integer> list = generator.generateList(1000000);
            for (int x : list){
                protocol.writeI32(x);
            }

            System.out.printf("Using %s protocol and sending 1000000 elements wrote %d bytes to the TMemoryBuffer\n",
                    protocolType, trans.length());
        } catch (TException e) {
            e.printStackTrace();
        }
    }

    /**
     * Prints an error when bad arguments provided.
     */
    private static void printError() {
        System.err.println("Bad arguments provided.");
        System.err.println("The program needs a serialisation method to be provided as program argument: " +
                "binary | json | compact");
    }

    /**
     * Returns the TProtocol based on an argument provided.
     *
     * @param protocol  the string representing a protocol type
     * @param trans  the memory buffer
     * @return the TProtocol of the required type
     */
    private static TProtocol readProtocol(String protocol, TMemoryBuffer trans){
        return switch (protocol) {
            case "binary" -> new TBinaryProtocol(trans);
            case "json" -> new TJSONProtocol(trans);
            case "compact" -> new TCompactProtocol(trans);
            default -> null;
        };
    }
}
