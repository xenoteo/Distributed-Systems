package xenoteo.com.github.lab1.lab.task1;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class JavaUdpServer {

    public static void main(String[] args)
    {
        System.out.println("JAVA UDP SERVER");

        int portNumber = 9008;
        try (DatagramSocket socket = new DatagramSocket(portNumber)) {
            while (true) {
                DatagramPacket receivePacket = receiveMsg(socket);
                if (receivePacket != null)
                    sendRespond("The message is received", socket, receivePacket);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static DatagramPacket receiveMsg(DatagramSocket socket){
        try {
            byte[] receiveBuffer = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
            socket.receive(receivePacket);
            String msg = new String(receiveBuffer);
            System.out.printf("Received message \"%s\" from %s\n", msg, receivePacket.getSocketAddress());
            return receivePacket;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void sendRespond(String msg, DatagramSocket socket, DatagramPacket receivePacket){
        try {
            byte[] sendBuffer = msg.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length,
                    receivePacket.getAddress(), receivePacket.getPort());
            socket.send(sendPacket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
