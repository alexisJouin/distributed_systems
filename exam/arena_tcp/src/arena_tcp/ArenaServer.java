package arena_tcp;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Vector;

public class ArenaServer {

    public static final int PORT = 6667;

    private Vector<Socket> clients = new Vector<Socket>();

    private HashMap<String, Integer> reservations = new HashMap<String, Integer>();

    private int capacity = 5000;

    public ArenaServer() {
        try {

            ServerSocket serverSocket = new ServerSocket(PORT);

            System.out.println("Arena Server waiting for connections");

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Arena Server connection received");
                clients.add(socket);
                Thread t = new Thread(new SubServer(this, socket));
                t.start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    Vector<Socket> getClients() {
        return clients;
    }

    HashMap<String, Integer> getReservations() {
        return reservations;
    }

    int getCapacity() {
        return capacity;
    }

    public static void main(String[] args) {
        new ArenaServer();
    }
}
