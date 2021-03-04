package xenoteo.com.github.lab1.homework;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class ClientInUdp implements Runnable{
    private boolean running;
    private final DatagramSocket udpSocket;

    public ClientInUdp(DatagramSocket udpSocket) {
        this.udpSocket = udpSocket;
        running = true;
    }

    @Override
    public void run() {
        try {
            while (running){
                byte[] receiveBuffer = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                udpSocket.receive(receivePacket);
                String udpResponse = new String(receiveBuffer);
                System.out.print("\u001B[34m");
                System.out.printf("[RECEIVED MESSAGE] %s\n", udpResponse);
                System.out.print("\u001B[0m");
            }
        } catch (IOException e) {
        }
    }

    public void finish(){
        running = false;
        udpSocket.close();
    }
}
