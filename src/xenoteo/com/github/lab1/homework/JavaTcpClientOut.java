package xenoteo.com.github.lab1.homework;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class JavaTcpClientOut implements Runnable{
    private final Socket socket;
    private final JavaTcpClientIn clientIn;

    public JavaTcpClientOut(Socket socket, JavaTcpClientIn clientIn) {
        this.socket = socket;
        this.clientIn = clientIn;
    }

    @Override
    public void run() {
        try {
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            Scanner input = new Scanner(System.in);
            while (true) {
                System.out.println("Enter the message (q to stop):");
                String msg = input.nextLine();
                if (msg.equals("q")) {
                    out.println("Finishing the transmission...");
                    clientIn.finish();
                    break;
                }
                out.println(msg);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
