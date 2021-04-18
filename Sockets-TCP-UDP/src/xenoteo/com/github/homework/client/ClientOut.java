package xenoteo.com.github.homework.client;

import xenoteo.com.github.homework.ColoredOutput;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * The client runnable sending messages to the server or to the clients using multicast messages.
 */
public class ClientOut implements Runnable{
    /**
     * The client's ID.
     */
    private final int clientId;
    /**
     * The client's TCP socket, which is needed to send TCP messages.
     */
    private final Socket tcpSocket;


    /**
     * The client runnable receiving UDP messages.
     * Needed to be kept to be closed in case if client is going to finish transmission,
     * which can be figured out only in the current thread, that is in thread sending messages.
     */
    private final ClientInUdp clientInUdp;
    /**
     * The client runnable receiving multicast messages.
     * Needed to be kept to be closed in case if client is going to finish transmission,
     * which can be figured out only in the current thread, that is in thread sending messages.
     */
    private final ClientInMulticast clientInMulticast;


    /**
     * The client's UDP socket, which is needed to send UDP messages.
     */
    private final DatagramSocket udpSocket;
    /**
     * The address for sending UDP messages.
     */
    private final InetAddress address;
    /**
     * The post number for sending UDP messages.
     */
    private final int portNumber;


    /**
     * The address for sending multicast messages.
     */
    private final InetAddress multicastAddress;
    /**
     * The port number for sending multicast messages.
     */
    private final int multicastPortNumber;


    /**
     * Scanned for reading the messages.
     */
    private final Scanner input;

    public ClientOut(Socket tcpSocket,
                     ClientInUdp clientInUdp, ClientInMulticast clientInMulticast,
                     DatagramSocket udpSocket, InetAddress address, int portNumber,
                     InetAddress multicastAddress, int multicastPortNumber) {
        this.clientId = tcpSocket.getLocalPort();
        this.tcpSocket = tcpSocket;

        this.clientInUdp = clientInUdp;
        this.clientInMulticast = clientInMulticast;

        this.udpSocket = udpSocket;
        this.address = address;
        this.portNumber = portNumber;

        this.multicastAddress = multicastAddress;
        this.multicastPortNumber = multicastPortNumber;

        this.input = new Scanner(System.in);
    }

    @Override
    public void run() {
        try {
            PrintWriter out = new PrintWriter(tcpSocket.getOutputStream(), true);
            while (true) {
                // reading the message
                System.out.println("Enter the message (q to stop, u to use UDP transmission, m to use multicast):");
                String msg = input.nextLine();

                // if client is going to finish transmission
                if (msg.equals("q")) {
                    out.println(Client.FINISH_MSG);
                    finish();
                    break;
                }

                // if using UDP to send the message
                else if (msg.equals("u")){
                    System.out.println("Enter the message to send using UDP transmission " +
                            "(c to cancel, empty line to finish the message):");
                    String data = readData();
                    if (!(data.equals("c") || data.isEmpty())){
                        byte[] sendBuffer = data.getBytes();
                        DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, address, portNumber);
                        udpSocket.send(sendPacket);
                    }
                }

                // if using multicast to send the message
                else if (msg.equals("m")){
                    System.out.println("Enter the message to send using UDP multicast transmission " +
                            "(c to cancel, empty line to finish the message):");
                    String data = readData();
                    if (!(data.equals("c") || data.isEmpty())){
                        data = clientId + ":\n" + data;
                        byte[] sendBuffer = data.getBytes();
                        DatagramPacket sendPacket =
                                new DatagramPacket(sendBuffer, sendBuffer.length, multicastAddress, multicastPortNumber);
                        udpSocket.send(sendPacket);
                    }
                }

                // if using TCP to send the message
                else if (!msg.isEmpty()){
                    out.println(msg);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalStateException e){
            ColoredOutput.printlnRed("[SERVER DISCONNECTED]");
        }
    }

    /**
     * Reads a multiline data from input and converts it to the one string.
     *
     * @return the string read
     */
    private String readData(){
        List<String> lines = new LinkedList<>();
        while (input.hasNextLine()){
            String line = input.nextLine();
            if (line.isEmpty())
                break;
            lines.add(line);
        }
        return String.join("\n", lines);
    }

    /**
     * Finishes the thread sending messages.
     */
    public void finish(){
        clientInUdp.finish();
        clientInMulticast.finish();
        input.close();
    }
}
