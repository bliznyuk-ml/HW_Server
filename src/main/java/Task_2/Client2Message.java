package Task_2;

import Task_1.Server;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client2Message {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        Socket client2 = new Socket();
        SocketAddress serverSocket =
                new InetSocketAddress(
                        InetAddress.getByName("localhost"),
                        ServerMessage.PORT1
                );
        System.out.println("Connection to server");
        client2.connect(serverSocket);
        System.out.println("Connected client 2:" + client2.getRemoteSocketAddress().toString() + "successfully");
        try (var in = client2.getInputStream();
             var reader = new BufferedReader(new InputStreamReader(in));
             var out = client2.getOutputStream();
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
        client2.close();
    }
}
