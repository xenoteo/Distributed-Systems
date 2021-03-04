package xenoteo.com.github.lab1.homework;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;

public class ServerUdpChannel implements Runnable{
    private final List<Socket> allClients;
    private final DatagramSocket serverUdpSocket;

    public ServerUdpChannel(List<Socket> allClients, DatagramSocket serverUdpSocket) {
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
                int senderId = receivePacket.getPort() - 1;
                ColoredOutput.printlnBlue("[RECEIVED MESSAGE] " + senderId + ":\n" + udpMsg);

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
