package xenoteo.com.github.lab1.homework;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

public class JavaTcpServer {

    public static void main(String[] args) {
        System.out.println("JAVA TCP SERVER");

        int portNumber = 12345;
        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(portNumber);
            List<Socket> clients = new LinkedList<>();
            while (true) {
                Socket clientSocket = serverSocket.accept();
                clients.add(clientSocket);
                System.out.printf("New client %d connected\n", clientSocket.getPort());
                Thread thread = new Thread(new JavaTcpServerClientChannel(clientSocket, clients));
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
