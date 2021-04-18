package xenoteo.com.github.lab.task1;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class JavaUdpClient {

    public static void main(String[] args)
    {
        System.out.println("JAVA UDP CLIENT");

        int portNumber = 9008;
        Scanner in = new Scanner(System.in);
        try (DatagramSocket socket = new DatagramSocket()) {
            InetAddress address = InetAddress.getByName("localhost");
            while (true) {
                System.out.print("Enter the message (q to stop): ");
                String msg = in.nextLine();
                if (msg.equals("q")) {
                    sendMsg("Finishing the transmission...", address, portNumber, socket);
                    break;
                }
                sendMsg(msg, address, portNumber, socket);
                receiveMsg(socket);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void sendMsg(String msg, InetAddress address, int portNumber, DatagramSocket socket){
        try {
            byte[] sendBuffer = msg.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, address, portNumber);
            socket.send(sendPacket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void receiveMsg(DatagramSocket socket){
        try {
            byte[] receiveBuffer = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
            socket.receive(receivePacket);
            String response = new String(receiveBuffer);
            System.out.printf("Received response \"%s\"\n", response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
