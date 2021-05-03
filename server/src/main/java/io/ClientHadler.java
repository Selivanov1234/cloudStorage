package io;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHadler implements Runnable{

    private final Socket socket;
    private final Server server;
    private DataInputStream is;
    private DataOutputStream os;



    public ClientHadler(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
    }

    public void sendMsg (String msg) throws IOException {
        os.writeUTF(msg);
        os.flush();
    }

    @Override
    public void run() {
        try {
        is = new DataInputStream(socket.getInputStream());
        os = new DataOutputStream(socket.getOutputStream());
            System.out.println("Connection is up!");
        while (true) {
            String msg = is.readUTF();
            System.out.println("received: " + msg);
            server.broadcast(msg);
        }
        }catch (Exception e){
            System.err.println("Connection failed!");
            server.kickClient(this);
        }
    }
}
