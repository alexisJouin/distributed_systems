package tp_chat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ChatServeur {

    static final int PORT = 6666;
    private int nbClients = 1;
    static ArrayList<Client> tabClients = new ArrayList<Client>();
    private Socket socket;

    public ChatServeur() {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("Serveur Prêt");

            while (true) {
                socket = serverSocket.accept();
                new ChatThread(socket).start();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("Erreur : " + ex);
        }
    }

    public static void main(String[] args) {
        ChatServeur serveur = new ChatServeur();
    }
}
