package xenoteo.com.github.lab1.homework;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.Socket;

// https://www.asciiart.eu/animals/dolphins
public class Client {
    public static final String FINISH_MSG = "Finishing the transmission...";

    public static void main(String[] args) {
        String hostName = "localhost";
        int portNumber = 12345;
        String multicastAddress = "230.0.0.0";
        int multicastPortNumber = 1234;

        try {
            Socket tcpSocket = new Socket(hostName, portNumber);
            int clientId = tcpSocket.getLocalPort();

            System.out.printf("CLIENT %d\n", clientId);

            InetAddress address = InetAddress.getByName(hostName);
            DatagramSocket udpSocket = new DatagramSocket(clientId + 1);

            InetAddress group = InetAddress.getByName(multicastAddress);
            MulticastSocket multicastSocket = new MulticastSocket(multicastPortNumber);
            multicastSocket.joinGroup(group);

            ClientInTcp clientInTcp = new ClientInTcp(tcpSocket);
            ClientInUdp clientInUdp = new ClientInUdp(udpSocket);
            ClientInMulticast clientInMulticast =
                    new ClientInMulticast(multicastSocket, clientId + 1);
            ClientOut clientOut = new ClientOut(tcpSocket,
                            clientInTcp, clientInUdp, clientInMulticast,
                            udpSocket, address, portNumber,
                            group, multicastPortNumber);
            clientInTcp.setClientOut(clientOut);
            new Thread(clientOut).start();
            new Thread(clientInTcp).start();
            new Thread(clientInMulticast).start();
            new Thread(clientInUdp).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static byte[] getFinishMsgBytes(){
        byte[] finishMsgBytes1024 = new byte[1024];
        byte[] finishMsgBytes = FINISH_MSG.getBytes();
        System.arraycopy(finishMsgBytes, 0, finishMsgBytes1024, 0, finishMsgBytes.length);
        return finishMsgBytes1024;
    }
}
