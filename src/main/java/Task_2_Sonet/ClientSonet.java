package Task_2_Sonet;

import Task_1.Server;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Scanner;

public class ClientSonet {
    public static void main(String[] args) throws IOException {
        //Scanner scanner = new Scanner(System.in);


        Socket client = new Socket();
        SocketAddress serverSocket = new InetSocketAddress(
                InetAddress.getByName("localhost"),
                ServerSonet.PORT3
        );
        client.connect(serverSocket);
        try (InputStream in = client.getInputStream();
             OutputStream out = new FileOutputStream("alice_copy.txt")) {
            while (in.available() > 0) {
                out.write(in.readNBytes(1024));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        client.close();
    }
}
