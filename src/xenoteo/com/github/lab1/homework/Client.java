package xenoteo.com.github.lab1.homework;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;

// https://www.asciiart.eu/animals/dolphins
public class Client {
    public static final String FINISH_MSG = "Finishing the transmission...";

    public static void main(String[] args) {
        System.out.println("JAVA TCP CLIENT");

        String hostName = "localhost";
        int portNumber = 12345;

        try {
            Socket tcpSocket = new Socket(hostName, portNumber);
            InetAddress address = InetAddress.getByName(hostName);
            DatagramSocket udpSocket = new DatagramSocket(tcpSocket.getLocalPort() + 1);
            ClientInTcp clientInTcp = new ClientInTcp(tcpSocket);
            ClientInUdp clientInUdp = new ClientInUdp(udpSocket);
            ClientOut clientOut = new ClientOut(tcpSocket, clientInTcp, clientInUdp, udpSocket, address, portNumber);
            new Thread(clientOut).start();
            new Thread(clientInTcp).start();
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
