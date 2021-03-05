package xenoteo.com.github.lab1.homework;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientInTcp implements Runnable{
    private BufferedReader in;
    private boolean running;
    private ClientOut clientOut;
    private final Socket tcpSocket;

    public ClientInTcp(Socket tcpSocket) {
        this.tcpSocket = tcpSocket;
        try {
            in = new BufferedReader(new InputStreamReader(tcpSocket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        running = true;
    }

    public void setClientOut(ClientOut clientOut) {
        this.clientOut = clientOut;
    }

    @Override
    public void run() {
        try {
            while (running){
                String tcpMsg = in.readLine();
                if (tcpMsg == null) { // if thread should already be closed or if the server has died
                    clientOut.finish();
                    break;
                }
                ColoredOutput.printlnBlue("[RECEIVED MESSAGE] " + reformatMsg(tcpMsg));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String reformatMsg(String msg){
        int spaceIndex = msg.indexOf(' ');
        return msg.substring(0, spaceIndex) + "\n" + msg.substring(spaceIndex + 1);
    }

    public void finish(){
        running = false;
    }
}
