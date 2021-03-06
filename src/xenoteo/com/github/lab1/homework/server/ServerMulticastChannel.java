package xenoteo.com.github.lab1.homework.server;

import xenoteo.com.github.lab1.homework.ColoredOutput;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * The server runnable for receiving multicast messages.
 */
public class ServerMulticastChannel implements Runnable{
    /**
     * The multicast socket.
     */
    private final DatagramSocket multicastSocket;

    public ServerMulticastChannel(DatagramSocket multicastSocket) {
        this.multicastSocket = multicastSocket;
    }

    @Override
    public void run() {
        try {
            while (true){
                // receiving the message
                byte[] receiveBuffer = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                multicastSocket.receive(receivePacket);

                // outputting the message
                String msg = new String(receiveBuffer);
                ColoredOutput.printlnBlue("[RECEIVED MESSAGE] " + (receivePacket.getPort() - 1) + ":" +
                        msg.substring(msg.indexOf("\n")));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
