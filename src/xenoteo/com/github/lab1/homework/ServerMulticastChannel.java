package xenoteo.com.github.lab1.homework;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class ServerMulticastChannel implements Runnable{
    private final DatagramSocket multicastSocket;

    public ServerMulticastChannel(DatagramSocket multicastSocket) {
        this.multicastSocket = multicastSocket;
    }

    @Override
    public void run() {
        try {
            while (true){
                byte[] receiveBuffer = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                multicastSocket.receive(receivePacket);

                String msg = new String(receiveBuffer);
                ColoredOutput.printlnBlue("[RECEIVED MESSAGE] " + (receivePacket.getPort() - 1) + ":" + msg.substring(msg.indexOf("\n")));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
