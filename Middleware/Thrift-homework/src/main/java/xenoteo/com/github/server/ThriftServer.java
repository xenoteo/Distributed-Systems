package xenoteo.com.github.server;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TJSONProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import xenoteo.com.github.thrift.gen.Transfer;

/**
 * The simple Thrift server receiving data from the Thrift client.
 */
public class ThriftServer {

    public static void main(String[] args) {
        try {
            if (args.length < 1) {
                printError();
                System.exit(1);
            }

            TProtocolFactory protocolFactory = readProtocol(args[0]);
            if (protocolFactory == null) {
                printError();
                System.exit(1);
            }

            Transfer.Processor<TransferHandler> processor = new Transfer.Processor<>(new TransferHandler());

            TServerTransport serverTransport = new TServerSocket(9080);

            TServer server = new TSimpleServer(new TServer.Args(serverTransport).protocolFactory(protocolFactory).processor(processor));

            System.out.println("Starting the simple server...");
            server.serve();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Prints an error when bad arguments provided.
     */
    private static void printError() {
        System.err.println("Bad arguments provided.");
        System.err.println("The server needs a serialisation method to be provided as program argument: " +
                "binary | json | compact");
    }

    /**
     * Returns the TProtocolFactory based on an argument provided.
     *
     * @param protocol  the string representing a protocol type
     * @return the TProtocolFactory of the required type
     */
    private static TProtocolFactory readProtocol(String protocol){
        return switch (protocol) {
            case "binary" -> new TBinaryProtocol.Factory();
            case "json" -> new TJSONProtocol.Factory();
            case "compact" -> new TCompactProtocol.Factory();
            default -> null;
        };
    }

}
