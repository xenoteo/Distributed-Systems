package xenoteo.com.github.homework.client;

import xenoteo.com.github.homework.ColoredOutput;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * The client runnable receiving TCP messages.
 */
public class ClientInTcp implements Runnable{
    /**
     * The client's TCP socket.
     */
    private final Socket tcpSocket;
    /**
     * The client runnable sending messages
     * Needed to be kept to be closed in case if server is dead,
     * which can be figured out only in the current thread, that is in thread receiving TCP messages.
     */
    private final ClientOut clientOut;


    public ClientInTcp(Socket tcpSocket, ClientOut clientOut) {
        this.tcpSocket = tcpSocket;
        this.clientOut = clientOut;
    }

    @Override
    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(tcpSocket.getInputStream()));
            while (true){
                // receiving the message from the server
                String tcpMsg = in.readLine();

                // if thread should already be closed or if the server has died
                // then close the socket and finish all the client's threads
                if (tcpMsg == null) {
                    clientOut.finish();
                    tcpSocket.close();
                    break;
                }

                // printing the message
                ColoredOutput.printlnBlue("[RECEIVED MESSAGE] " + reformatMsg(tcpMsg));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adding additional line makes a message of the same format as the messages of the other types.
     *
     * @param msg  the message
     * @return the reformatted message
     */
    private String reformatMsg(String msg){
        int spaceIndex = msg.indexOf(' ');
        return msg.substring(0, spaceIndex) + "\n" + msg.substring(spaceIndex + 1);
    }
}
