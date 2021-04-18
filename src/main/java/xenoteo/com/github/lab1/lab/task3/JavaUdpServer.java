package xenoteo.com.github.lab1.lab.task3;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

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
                int number = ByteBuffer.wrap(receiveBuffer).order(ByteOrder.LITTLE_ENDIAN).getInt();
                System.out.printf("Received number %d from %s\n", number, receivePacket.getSocketAddress());

                byte[] sendBuffer = ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putInt(number + 1).array();
                DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length,
                        receivePacket.getAddress(), receivePacket.getPort());
                socket.send(sendPacket);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
