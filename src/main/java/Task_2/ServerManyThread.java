package Task_2;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class ServerManyThread {
    public static final int PORT = 10_001;

    public static void main(String[] args) {

        List<Socket> users = new ArrayList<Socket>();
        String mess;

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Wait for connection");
            while (true) {
                Socket client = serverSocket.accept();
                System.out.println("Client connected at" + client.getRemoteSocketAddress().toString());
                users.add(client);
                requestForAll(users, client);
                //response(users, client, mess);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void requestForAll(List<Socket> users, Socket client) {
        String[] message = new String[1];
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.submit(() -> {
            try (InputStream in = client.getInputStream();
                 BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in))) {
                message[0] = bufferedReader.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            for (Socket s : users) {
                if (s != client) {
                    System.out.println(s);
                    System.out.println(client);
                    try (OutputStream out = s.getOutputStream();
                         PrintStream printStream = new PrintStream(out, true)) {
                        System.out.println("Message from client " + client.getRemoteSocketAddress().toString() + ": " + message);
                        printStream.println("you ask: " + message);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
    }

   /* private static void response(List<Socket> users, Socket client, String message) {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.submit(() -> {
        for (Socket s : users) {
            if (s != client) {
                System.out.println(s);
                System.out.println(client);
                try (OutputStream out = s.getOutputStream();
                     PrintStream printStream = new PrintStream(out, true)) {
                    System.out.println("Message from client " + client.getRemoteSocketAddress().toString() + ": " + message);
                    printStream.println("you ask: " + message);
                } catch (IOException e) {
                    throw new RuntimeException(e);

                }
            }
        }
        });
    }*/
}

   /* public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Wait for connection");
            while (true) {
                Socket client = serverSocket.accept();
                executorService.submit(() -> {
                    System.out.println("Client connected at" + client.getRemoteSocketAddress().toString());
                    try (InputStream in = client.getInputStream();
                         BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
                         OutputStream out = client.getOutputStream();
                         PrintStream printStream = new PrintStream(out, true)) {
                        bufferedReader.lines()
                                .forEach(line -> {
                                    System.out.println("Message from client " + client.getRemoteSocketAddress().toString() + ": " + line);
                                    printStream.println("you ask: " + line);
                                });
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}*/


