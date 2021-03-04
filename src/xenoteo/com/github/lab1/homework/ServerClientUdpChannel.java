package xenoteo.com.github.lab1.homework;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;

public class ServerClientUdpChannel implements Runnable{
    private final int clientId;
    private final List<Socket> allClients;
    private final DatagramSocket serverUdpSocket;

    public ServerClientUdpChannel(int clientId, List<Socket> allClients, DatagramSocket serverUdpSocket) {
        this.clientId = clientId;
        this.allClients = allClients;
        this.serverUdpSocket = serverUdpSocket;
    }

    @Override
    public void run() {
        try{
            InetAddress address = InetAddress.getByName("localhost");
            byte[] finishMsgBytes = Client.getFinishMsgBytes();
            while (true){
                byte[] receiveBuffer = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                serverUdpSocket.receive(receivePacket);

                if (Arrays.equals(finishMsgBytes, receiveBuffer)){
                    break;
                }

                String udpMsg = new String(receiveBuffer);
                System.out.printf("Received msg from client %d:\n%s\n", clientId, udpMsg);

                byte[] sendBuffer = ("Client " + clientId + " send msg:\n" + udpMsg + "\n").getBytes();
                for (Socket client : allClients) {
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
