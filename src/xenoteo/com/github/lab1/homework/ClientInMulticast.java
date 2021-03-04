package xenoteo.com.github.lab1.homework;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.MulticastSocket;

public class ClientInMulticast implements Runnable{
    private boolean running;
    private final DatagramSocket multicastSocket;
    private final int clientPortNumber;

    public ClientInMulticast(MulticastSocket multicastSocket, int clientPortNumber) {
        this.multicastSocket = multicastSocket;
        running = true;
        this.clientPortNumber = clientPortNumber;
    }

    @Override
    public void run() {
        try {
            while (running){
                byte[] receiveBuffer = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                multicastSocket.receive(receivePacket);

                if (receivePacket.getPort() == clientPortNumber)
                    continue;

                String msg = new String(receiveBuffer);
                ColoredOutput.printlnBlue("[RECEIVED MESSAGE] " + msg);
            }
        } catch (IOException e) {
        }
    }

    public void finish(){
        running = false;
        multicastSocket.close();
    }
}
