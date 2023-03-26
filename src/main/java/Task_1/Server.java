package org.example;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static final int PORT = 10_000;

    public static void main(String[] args) throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(PORT);
             InputStream in = new FileInputStream("alice.txt")) {
            Socket client = serverSocket.accept();
            try (OutputStream out = client.getOutputStream()) {
                while (in.available() > 0) {
                    byte[] bytes = in.readNBytes(1024);
                    out.write(bytes);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
