package xenoteo.com.github.lab1.homework.server;

import xenoteo.com.github.lab1.homework.ColoredOutput;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.util.List;

/**
 * The server runnable for receiving UDP messages.
 */
public class ServerUdpChannel implements Runnable{
    /**
     * The list of all clients.
     */
    private final List<Socket> allClients;
    /**
     * The UDP socket.
     */
    private final DatagramSocket serverUdpSocket;

    public ServerUdpChannel(List<Socket> allClients, DatagramSocket serverUdpSocket) {
        this.allClients = allClients;
        this.serverUdpSocket = serverUdpSocket;
    }

    @Override
    public void run() {
        try{
            InetAddress address = InetAddress.getByName("localhost");
            while (true){
                // receiving the message
                byte[] receiveBuffer = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                serverUdpSocket.receive(receivePacket);

                // outputting the message
                String udpMsg = new String(receiveBuffer);
                int senderId = receivePacket.getPort() - 1;
                ColoredOutput.printlnBlue("[RECEIVED MESSAGE] " + senderId + ":\n" + udpMsg);

                // sending the message to all the other clients
                byte[] sendBuffer = (senderId + ":\n" + udpMsg + "\n").getBytes();
                for (Socket client : allClients) {
                    if (client.getPort() == senderId) continue;
                    DatagramPacket sendPacket =
                            new DatagramPacket(sendBuffer, sendBuffer.length, address, client.getPort() + 1);
                    serverUdpSocket.send(sendPacket);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
