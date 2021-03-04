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
                String udpMsg = new String(receiveBuffer);
                ColoredOutput.printlnBlue("[RECEIVED MESSAGE] " + udpMsg);
            }
        } catch (IOException e) {
        }
    }

    public void finish(){
        running = false;
        udpSocket.close();
    }
}
