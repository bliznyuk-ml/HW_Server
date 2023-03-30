package Task_2;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class ServerManyThread {
    public static final int PORT = 10_000;

    public static void main(String[] args) {

        List<Socket> users = new ArrayList<Socket>();

        ExecutorService executorService = Executors.newFixedThreadPool(2);

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Wait for connection");
            while (true) {
                Socket client = serverSocket.accept();
                System.out.println("Client connected at" + client.getRemoteSocketAddress().toString());
                users.add(client);
                requestForAll(users, executorService, client);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void requestForAll(List<Socket> users, ExecutorService executorService, Socket client) {

        //executorService.submit(() -> {
        try (InputStream in = client.getInputStream();
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));) {

            for (Socket s : users) {
                if (s != client) {
                    try (OutputStream out = s.getOutputStream();
                         PrintStream printStream = new PrintStream(out, true)) {
                        bufferedReader.lines()
                                .forEach(line -> {
                                    System.out.println("Message from client " + client.getRemoteSocketAddress().toString() + ": " + line);
                                    printStream.println("you ask: " + line);
                                });
                    } catch (IOException e) {
                        throw new RuntimeException(e);

                    }
                }
                // });
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
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
                                    System.out.println("Message from client "+ client.getRemoteSocketAddress().toString()+ ": " + line);
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
    }*/


