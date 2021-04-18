package xenoteo.com.github.homework.server;

import xenoteo.com.github.homework.ColoredOutput;

import java.io.IOException;
import java.net.*;
import java.util.LinkedList;
import java.util.List;

/**
 * The class executing server.
 */
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
            // initializing TCP and UDP sockets
            serverTcpSocket = new ServerSocket(portNumber);
            serverUdpSocket = new DatagramSocket(portNumber);

            // initializing the list of clients' sockets
            List<Socket> clients = new LinkedList<>();

            // creating and starting UDP channel receiving UDP messages
            ServerUdpChannel udpChannel = new ServerUdpChannel(clients, serverUdpSocket);
            new Thread(udpChannel).start();

            // creating and starting channel receiving multicast messages
            InetAddress group = InetAddress.getByName(multicastAddress);
            multicastSocket = new MulticastSocket(multicastPortNumber);
            multicastSocket.joinGroup(group);
            ServerMulticastChannel multicastChannel = new ServerMulticastChannel(multicastSocket);
            new Thread(multicastChannel).start();

            while (true) {
                // accepting a new client and adding it to the list of clients
                Socket clientSocket = serverTcpSocket.accept();
                clients.add(clientSocket);
                ColoredOutput.printlnGreen("[NEW CLIENT CONNECTED] " + clientSocket.getPort());

                // creating and starting a personal client's channel for communication with the server
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
