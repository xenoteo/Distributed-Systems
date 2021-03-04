package xenoteo.com.github.lab1.homework;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class ClientOut implements Runnable{
    private final int clientId;
    private final Socket tcpSocket;
    private final ClientInTcp clientInTcp;
    private final ClientInUdp clientInUdp;
    private final ClientInMulticast clientInMulticast;
    private final DatagramSocket udpSocket;
    private final InetAddress address;
    private final int portNumber;
    private final InetAddress multicastAddress;
    protected final int multicastPortNumber;

    public ClientOut(Socket tcpSocket,
                     ClientInTcp clientInTcp, ClientInUdp clientInUdp, ClientInMulticast clientInMulticast,
                     DatagramSocket udpSocket, InetAddress address, int portNumber,
                     InetAddress multicastAddress, int multicastPortNumber) {
        this.clientId = tcpSocket.getLocalPort();
        this.tcpSocket = tcpSocket;
        this.clientInTcp = clientInTcp;
        this.clientInUdp = clientInUdp;
        this.clientInMulticast = clientInMulticast;
        this.udpSocket = udpSocket;
        this.address = address;
        this.portNumber = portNumber;
        this.multicastAddress = multicastAddress;
        this.multicastPortNumber = multicastPortNumber;
    }

    @Override
    public void run() {
        try {
            PrintWriter out = new PrintWriter(tcpSocket.getOutputStream(), true);
            Scanner input = new Scanner(System.in);
            while (true) {
                System.out.println("Enter the message (q to stop, u to use UDP transmission, m to use multicast):");
                String msg = input.nextLine();
                if (msg.equals("q")) {
                    out.println(Client.FINISH_MSG);

                    byte[] sendBuffer = Client.FINISH_MSG.getBytes();
                    DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, address, portNumber);
                    udpSocket.send(sendPacket);

                    clientInTcp.finish();
                    clientInUdp.finish();
                    clientInMulticast.finish();
                    break;
                }
                else if (msg.equals("u")){
                    System.out.println("Enter the message to send using UDP transmission " +
                            "(c to cancel, empty line to finish the message):");
                    List<String> lines = new LinkedList<>();
                    while (input.hasNextLine()){
                        String line = input.nextLine();
                        if (line.isEmpty())
                            break;
                        lines.add(line);
                    }
                    String data = String.join("\n", lines);

                    if (!(data.equals("c") || data.isEmpty())){
                        byte[] sendBuffer = data.getBytes();
                        DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, address, portNumber);
                        udpSocket.send(sendPacket);
                    }
                }
                else if (msg.equals("m")){
                    System.out.println("Enter the message to send using UDP transmission " +
                            "(c to cancel, empty line to finish the message):");
                    List<String> lines = new LinkedList<>();
                    while (input.hasNextLine()){
                        String line = input.nextLine();
                        if (line.isEmpty())
                            break;
                        lines.add(line);
                    }
                    String data = String.join("\n", lines);

                    if (!(data.equals("c") || data.isEmpty())){
                        data = clientId + ":\n" + data;
                        byte[] sendBuffer = data.getBytes();
                        DatagramPacket sendPacket =
                                new DatagramPacket(sendBuffer, sendBuffer.length, multicastAddress, multicastPortNumber);
                        udpSocket.send(sendPacket);
                    }
                }
                else if (!msg.isEmpty()){
                    out.println(msg);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
