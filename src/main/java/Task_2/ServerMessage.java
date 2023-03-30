package Task_2;

/*Клиент посылает через сервер сообщение другому клиенту*/

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import static Task_2.ServerMessage.PORT1;

class ServerThread extends Thread {


    public ServerThread(String name) {
        super(name);
    }

    public void run() {
        ServerSocket server = null;
        try {
            server = new ServerSocket(PORT1);
            System.out.println("Wait for connection...");
            Socket remoteClient = null;
            remoteClient = server.accept();
            System.out.println("Client connected at" + remoteClient.getRemoteSocketAddress().toString());
            try (InputStream in = remoteClient.getInputStream();
                 BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
                 OutputStream out = remoteClient.getOutputStream();
                 PrintStream printStream = new PrintStream(out, true)) {
                bufferedReader.lines()
                        .forEach(line -> {
                            System.out.println("Message from client:" + line);
                            printStream.println("you ask: " + line);
                        });
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

public class ServerMessage {

    public static final int PORT1 = 10_000;

    public static void main(String[] args) {

        System.out.println("Main thread started...");
        ServerThread t = new ServerThread("firstThread");
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {

            System.out.printf("%s has been interrupted", t.getName());
        }
        System.out.println("Main thread finished...");

    }
}

