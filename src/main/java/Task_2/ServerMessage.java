package Task_2;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server_Message {
    public static final int PORT = 10_001;

    public static void main(String[] args) throws IOException {
        try (ServerSocket server = new ServerSocket(PORT)){
            System.out.println("Wait for connection");
            Socket remoteClient = server.accept();
            System.out.println("Client connected: " + remoteClient.getRemoteSocketAddress().toString());
            try (InputStream in = remoteClient.getInputStream();
                 BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
                 OutputStream out = remoteClient.getOutputStream();
                 PrintStream printStream = new PrintStream(out, true)) {
                bufferedReader.lines()
                        .forEach(line -> {
                            System.out.println("Message from client: " + line);
                            printStream.println("You ask: " + line);
                        });
            }
        }
    }
}
