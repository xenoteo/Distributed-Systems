package xenoteo.com.github.lab1.homework;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

public class Server {

    public static void main(String[] args) {
        System.out.println("JAVA TCP SERVER");

        int portNumber = 12345;
        ServerSocket serverTcpSocket = null;
        DatagramSocket serverUdpSocket = null;

        try {
            serverTcpSocket = new ServerSocket(portNumber);
            serverUdpSocket = new DatagramSocket(portNumber);
            List<Socket> clients = new LinkedList<>();
            while (true) {
                Socket clientSocket = serverTcpSocket.accept();
                clients.add(clientSocket);
                System.out.printf("New client %d connected\n", clientSocket.getPort());
                ServerClientUdpChannel udpChannel =
                        new ServerClientUdpChannel(clientSocket.getPort(), clients, serverUdpSocket);
                ServerClientTcpChannel tcpChannel = new ServerClientTcpChannel(clientSocket, clients);
                new Thread(udpChannel).start();
                new Thread(tcpChannel).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (serverTcpSocket != null)
                    serverTcpSocket.close();
                if (serverUdpSocket != null)
                    serverUdpSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
