package xenoteo.com.github.lab1.homework;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

public class JavaTcpServerClientChannel implements Runnable{
    private final List<Socket> allClients;
    private BufferedReader in;
    private final int clientId;

    public JavaTcpServerClientChannel(Socket clientSocket, List<Socket> allClients) {
        this.allClients = allClients;
        clientId = clientSocket.getPort();
        try {
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                String msg = in.readLine();
                if (msg != null && msg.equals("Finishing the transmission...")){
                    System.out.printf("Client %d disconnected\n", clientId);
                    in.close();
                    break;
                }
                if (msg != null){
                    System.out.printf("Received msg from client %d: %s\n", clientId, msg);
                    for (Socket client : allClients) {
                        PrintWriter out = new PrintWriter(client.getOutputStream(), true);
                        out.printf("Client %d send msg \"%s\"\n", clientId, msg);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
