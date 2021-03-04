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
    private final Socket tcpSocket;
    private final ClientInTcp clientInTcp;
    private final ClientInUdp clientInUdp;
    private final DatagramSocket udpSocket;
    private final InetAddress address;
    private final int portNumber;

    public ClientOut(Socket tcpSocket, ClientInTcp clientInTcp, ClientInUdp clientInUdp, DatagramSocket udpSocket, InetAddress address, int portNumber) {
        this.tcpSocket = tcpSocket;
        this.clientInTcp = clientInTcp;
        this.clientInUdp = clientInUdp;
        this.udpSocket = udpSocket;
        this.address = address;
        this.portNumber = portNumber;
    }

    @Override
    public void run() {
        try {
            PrintWriter out = new PrintWriter(tcpSocket.getOutputStream(), true);
            Scanner input = new Scanner(System.in);
            while (true) {
                System.out.println("Enter the message (q to stop, u to use UDP transmission):");
                String msg = input.nextLine();
                if (msg.equals("q")) {
                    out.println(Client.FINISH_MSG);

                    byte[] sendBuffer = Client.FINISH_MSG.getBytes();
                    DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, address, portNumber);
                    udpSocket.send(sendPacket);

                    clientInTcp.finish();
                    clientInUdp.finish();
                    break;
                }
                if (msg.equals("u")){
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

                    if (!data.equals("c")){
                        byte[] sendBuffer = data.getBytes();
                        DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, address, portNumber);
                        udpSocket.send(sendPacket);
                    }
                }
                else {
                    out.println(msg);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
