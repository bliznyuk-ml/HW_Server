package Task_2;

import Task_1.Server;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client1Message {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        Socket client1 = new Socket();
        SocketAddress serverSocket =
                new InetSocketAddress(
                        InetAddress.getByName("localhost"),
                        ServerManyThread.PORT
                );
        System.out.println("Connection to server");
        client1.connect(serverSocket);
        System.out.println("Connected client 1:" + client1.getRemoteSocketAddress().toString() + "successfully");
        try (var in = client1.getInputStream();
             var reader = new BufferedReader(new InputStreamReader(in));
             var out = client1.getOutputStream();
             var print = new PrintStream(out, true)) {
            String line;
            while (true) {
                System.out.print(">>> ");
                line = scanner.nextLine();
                if ("exit".equalsIgnoreCase(line.trim())) {
                    break;
                }
                print.println(line);
                String response = reader.readLine();
                System.out.println("Server answered:" + response);
            }
        }
        client1.close();
    }
}
