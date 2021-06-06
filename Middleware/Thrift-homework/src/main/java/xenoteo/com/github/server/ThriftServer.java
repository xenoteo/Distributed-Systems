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

public class ThriftServer {

    public static void main(String [] args) {
        try {
            Transfer.Processor<TransferHandler> processor = new Transfer.Processor<>(new TransferHandler());

            TServerTransport serverTransport = new TServerSocket(9080);

            TProtocolFactory protocolFactory = new TBinaryProtocol.Factory();
//            TProtocolFactory protocolFactory = new TJSONProtocol.Factory();
//            TProtocolFactory protocolFactory = new TCompactProtocol.Factory();

            TServer server = new TSimpleServer(new TServer.Args(serverTransport).protocolFactory(protocolFactory).processor(processor));

            System.out.println("Starting the simple server...");
            server.serve();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
