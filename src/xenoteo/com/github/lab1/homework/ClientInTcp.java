package xenoteo.com.github.lab1.homework;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientInTcp implements Runnable{
    private BufferedReader in;
    private boolean running;

    public ClientInTcp(Socket tcpSocket) {
        try {
            in = new BufferedReader(new InputStreamReader(tcpSocket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        running = true;
    }

    @Override
    public void run() {
        try {
            while (running){
                String tcpResponse = in.readLine();
                if (tcpResponse != null) {
                    System.out.print("\u001B[34m");
                    System.out.printf("[RECEIVED MESSAGE] %s\n", tcpResponse);
                    System.out.print("\u001B[0m");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void finish(){
        running = false;
    }
}
