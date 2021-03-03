package xenoteo.com.github.lab1.homework;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class JavaTcpClientIn implements Runnable{
    private BufferedReader in;
    private boolean running;

    public JavaTcpClientIn(Socket socket) {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        running = true;
    }

    @Override
    public void run() {
        try {
            while (running){
                String response = in.readLine();
                if (response != null) {
                    System.out.print("\u001B[34m");
                    System.out.printf("RECEIVED MESSAGE: %s\n", response);
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
