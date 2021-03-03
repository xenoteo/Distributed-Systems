package xenoteo.com.github.lab1.homework;

import java.net.Socket;

public class JavaTcpClient {

    public static void main(String[] args) {
        System.out.println("JAVA TCP CLIENT");

        String hostName = "localhost";
        int portNumber = 12345;

        try {
            Socket socket = new Socket(hostName, portNumber);
            JavaTcpClientIn clientIn = new JavaTcpClientIn(socket);
            JavaTcpClientOut clientOut = new JavaTcpClientOut(socket, clientIn);
            new Thread(clientOut).start();
            new Thread(clientIn).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
