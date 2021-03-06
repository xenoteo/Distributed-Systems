package xenoteo.com.github.lab1.homework.client;

import xenoteo.com.github.lab1.homework.ColoredOutput;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.MulticastSocket;

/**
 * The client runnable receiving multicast messages.
 */
public class ClientInMulticast implements Runnable{
    /**
     * The multicast socket.
     */
    private final DatagramSocket multicastSocket;
    /**
     * The current client's port number.
     */
    private final int clientPortNumber;

    public ClientInMulticast(MulticastSocket multicastSocket, int clientPortNumber) {
        this.multicastSocket = multicastSocket;
        this.clientPortNumber = clientPortNumber;
    }

    @Override
    public void run() {
        try {
            while (true){
                // receiving the message
                byte[] receiveBuffer = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                multicastSocket.receive(receivePacket);

                // if from the current client then not outputting it
                if (receivePacket.getPort() == clientPortNumber)
                    continue;

                // outputting the received message
                String msg = new String(receiveBuffer);
                ColoredOutput.printlnBlue("[RECEIVED MESSAGE] " + msg);
            }
        } catch (IOException e) {
        }
    }

    /**
     * Finishes the thread receiving multicast messages.
     */
    public void finish(){
        multicastSocket.close();
    }
}
