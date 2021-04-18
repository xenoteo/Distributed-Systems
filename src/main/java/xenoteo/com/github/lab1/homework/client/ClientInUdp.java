package xenoteo.com.github.lab1.homework.client;

import xenoteo.com.github.lab1.homework.ColoredOutput;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * The client runnable receiving UDP messages.
 */
public class ClientInUdp implements Runnable{
    /**
     * The client's UDP socket.
     */
    private final DatagramSocket udpSocket;

    public ClientInUdp(DatagramSocket udpSocket) {
        this.udpSocket = udpSocket;
    }

    @Override
    public void run() {
        try {
            while (true){
                // receiving the message and printing it
                byte[] receiveBuffer = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                udpSocket.receive(receivePacket);
                String udpMsg = new String(receiveBuffer);
                ColoredOutput.printlnBlue("[RECEIVED MESSAGE] " + udpMsg);
            }
        } catch (IOException e) {
        }
    }

    /**
     * Finishes the thread receiving UDP messages.
     */
    public void finish(){
        udpSocket.close();
    }
}
