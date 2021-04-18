package xenoteo.com.github.homework.server;

import xenoteo.com.github.homework.ColoredOutput;
import xenoteo.com.github.homework.client.Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

/**
 * The server runnable for server-client one-to-one communication.
 */
public class ServerClientTcpChannel implements Runnable{
    /**
     * The client's socket.
     */
    private final Socket clientSocket;
    /**
     * The list of all the clients.
     */
    private final List<Socket> allClients;
    /**
     * The client's ID.
     */
    private final int clientId;

    public ServerClientTcpChannel(Socket clientSocket, List<Socket> allClients) {
        this.clientSocket = clientSocket;
        this.allClients = allClients;
        clientId = clientSocket.getPort();
    }

    @Override
    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            while (true) {
                String tcpMsg = in.readLine();
                // if client is dead or if client is going to finish the transmission
                if (tcpMsg == null || tcpMsg.equals(Client.FINISH_MSG)){
                    ColoredOutput.printlnPurple("[CLIENT DISCONNECTED] " + clientId);
                    in.close();
                    allClients.remove(clientSocket);
                    break;
                }

                // outputting the message and sending the message to all the other clients
                ColoredOutput.printlnBlue("[RECEIVED MESSAGE] " + clientId + ":\n" + tcpMsg);
                for (Socket client : allClients) {
                    if (client.getPort() == clientId) continue;
                    PrintWriter out = new PrintWriter(client.getOutputStream(), true);
                    out.printf("%d: %s\n", clientId, tcpMsg);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
