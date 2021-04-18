package xenoteo.com.github.homework.client;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.Socket;

/**
 * The class executing client.
 */
public class Client {
    /**
     * The final message which is send to the server when the client is finished.
     */
    public static final String FINISH_MSG = "Finishing the transmission...";

    public static void main(String[] args) {
        String hostName = "localhost";
        int portNumber = 12345;

        String multicastAddress = "230.0.0.0";
        int multicastPortNumber = 1234;

        try {
            // setting up TCP channel
            Socket tcpSocket = new Socket(hostName, portNumber);
            int clientId = tcpSocket.getLocalPort();

            System.out.printf("CLIENT %d\n", clientId);

            // setting up UDP channel
            InetAddress address = InetAddress.getByName(hostName);
            DatagramSocket udpSocket = new DatagramSocket(clientId + 1);

            // setting up multicast UDP channel
            InetAddress group = InetAddress.getByName(multicastAddress);
            MulticastSocket multicastSocket = new MulticastSocket(multicastPortNumber);
            multicastSocket.joinGroup(group);

            // starting all client's threads
            ClientInUdp clientInUdp = new ClientInUdp(udpSocket);
            ClientInMulticast clientInMulticast = new ClientInMulticast(multicastSocket, clientId + 1);
            ClientOut clientOut = new ClientOut(tcpSocket,
                            clientInUdp, clientInMulticast,
                            udpSocket, address, portNumber,
                            group, multicastPortNumber);
            ClientInTcp clientInTcp = new ClientInTcp(tcpSocket, clientOut);
            new Thread(clientOut).start();
            new Thread(clientInTcp).start();
            new Thread(clientInMulticast).start();
            new Thread(clientInUdp).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
