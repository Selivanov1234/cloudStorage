package io;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Server {

    private ConcurrentLinkedQueue<ClientHadler> clients;

    public Server(int port) {

        clients = new ConcurrentLinkedQueue<>();
        try {
            ServerSocket server = new ServerSocket(port);
            System.out.println("Server is up!");
            while (true) {
                Socket socket = server.accept();
                System.out.println("Client accepted!");
                ClientHadler handler = new ClientHadler(socket, this);
                clients.add(handler);
                new Thread(handler).start();
            }
        }catch (Exception e) {
            System.err.println("Server is down!");
        }
    }

    public void broadcast (String msg) throws IOException {
        for (ClientHadler client: clients) {
            client.sendMsg(msg);
        }
    }
    public void kickClient(ClientHadler handler) {
        clients.remove(handler);
    }
}
