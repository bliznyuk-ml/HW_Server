package Task_2_Sonet;

import Task_1.Server;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.Scanner;

public class ClientSonet {

    public static void getSonet(){
        Socket client = new Socket();
        SocketAddress serverSocket = null;
        try {
            serverSocket = new InetSocketAddress(
                    InetAddress.getByName("localhost"),
                    ServerSonet.PORT3
            );
            client.connect(serverSocket);

            try(InputStream in = client.getInputStream()) {

                Reader reader = new InputStreamReader(in, StandardCharsets.UTF_8);
                BufferedReader br = new BufferedReader(reader);

                StringBuilder sb = new StringBuilder();
                String line;

                while ((line = br.readLine()) != null) {
                    sb.append(line);
                    sb.append('\n');
                }
                br.close();
                String sonet = sb.toString();
                // System.out.println(sonet);
                String[] sonets = sonet.split("#");
                System.out.println(sonets.length);
                Random rnd = new Random();
                int s = rnd.nextInt(sonets.length) + 1;
                System.out.println(sonets[s]);
            }




            client.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        getSonet();

    }
}
