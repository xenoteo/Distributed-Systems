package xenoteo.com.github.server;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

/**
 * The municipal office server handling clients' requests.
 */
public class MunicipalOfficeServer {

    private Server server;

    public static void main(String[] args) {
        final MunicipalOfficeServer server = new MunicipalOfficeServer();
        server.start();
        server.blockUntilShutdown();
    }

    private void start() {
        try {
            server = ServerBuilder.forPort(50051)
                    .addService(new MunicipalOffice())
                    .build();
            server.start();
            System.out.println("server started on " + server.getPort());

            Runtime.getRuntime().addShutdownHook(new Thread() {
                @Override
                public void run() {
                    System.err.println("shutting down gRPC server since JVM is shutting down");
                    MunicipalOfficeServer.this.stop();
                    System.err.println("server shut down");
                }
            });
        } catch (IOException e) {
            System.err.println("unable to start gRPC server");
        }
    }

    private void stop() {
        if (server != null) {
            server.shutdown();
        }
    }

    private void blockUntilShutdown() {
        if (server != null) {
            try {
                server.awaitTermination();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
