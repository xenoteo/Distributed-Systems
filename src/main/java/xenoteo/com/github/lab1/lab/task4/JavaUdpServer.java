package xenoteo.com.github.lab1.lab.task4;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class JavaUdpServer {

    public static void main(String[] args)
    {
        System.out.println("JAVA UDP SERVER");

        int portNumber = 9008;
        try (DatagramSocket socket = new DatagramSocket(portNumber)) {
            while (true) {
                byte[] receiveBuffer = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                socket.receive(receivePacket);
                String msg = new String(receiveBuffer);
                System.out.printf("Received message \"%s\" from %s\n", msg, receivePacket.getSocketAddress());

                byte[] sendBuffer;
                if (msg.contains("[Ping Java]")){
                    sendBuffer = "[Pong Java]".getBytes();
                }
                else if (msg.contains("[Ping Python]")){
                    sendBuffer = "[Pong Python]".getBytes();
                }
                else {
                    sendBuffer = "The message is received".getBytes();
                }
                DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length,
                        receivePacket.getAddress(), receivePacket.getPort());
                socket.send(sendPacket);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
