package xenoteo.com.github.lab1.homework;

import java.io.IOException;
import java.net.*;
import java.util.LinkedList;
import java.util.List;

public class Server {

    public static void main(String[] args) {
        System.out.println("SERVER");

        int portNumber = 12345;
        ServerSocket serverTcpSocket = null;
        DatagramSocket serverUdpSocket = null;
        MulticastSocket multicastSocket = null;

        String multicastAddress = "230.0.0.0";
        int multicastPortNumber = 1234;

        try {
            serverTcpSocket = new ServerSocket(portNumber);
            serverUdpSocket = new DatagramSocket(portNumber);
            List<Socket> clients = new LinkedList<>();

            ServerUdpChannel udpChannel = new ServerUdpChannel(clients, serverUdpSocket);
            new Thread(udpChannel).start();

            InetAddress group = InetAddress.getByName(multicastAddress);
            multicastSocket = new MulticastSocket(multicastPortNumber);
            multicastSocket.joinGroup(group);
            ServerMulticastChannel multicastChannel = new ServerMulticastChannel(multicastSocket);
            new Thread(multicastChannel).start();

            while (true) {
                Socket clientSocket = serverTcpSocket.accept();
                clients.add(clientSocket);
                ColoredOutput.printlnGreen("[NEW CLIENT CONNECTED] " + clientSocket.getPort());
                ServerClientTcpChannel tcpChannel = new ServerClientTcpChannel(clientSocket, clients);
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
                if (multicastSocket != null)
                    multicastSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
