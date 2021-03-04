package xenoteo.com.github.lab1.homework;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

public class ServerClientTcpChannel implements Runnable{
    private final Socket clientSocket;
    private final List<Socket> allClients;
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
                if (tcpMsg == null || tcpMsg.equals(Client.FINISH_MSG)){
                    ColoredOutput.printlnPurple("[CLIENT DISCONNECTED] " + clientId);
                    in.close();
                    allClients.remove(clientSocket);
                    break;
                }

                ColoredOutput.printlnBlue("[RECEIVED MESSAGE] " + clientId + ":\n" + tcpMsg);
                for (Socket client : allClients) {
                    if (client.getPort() == clientId) continue;
                    PrintWriter out = new PrintWriter(client.getOutputStream(), true);
                    out.printf("%d:\n%s\n", clientId, tcpMsg);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
