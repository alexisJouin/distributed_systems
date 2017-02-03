package tp_chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class ChatServeur implements Runnable {

    static final int PORT = 6666;
    private int nbClients = 1;
    static  Vector<Socket> tabClients = new Vector<Socket>();
    private PrintWriter sortie;
    private BufferedReader entree;
    private Socket socket;
    private String clientName;

    public ChatServeur() {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("Serveur Prêt");

            while (true) {
                socket = serverSocket.accept();
                sortie = new PrintWriter(socket.getOutputStream());
                entree = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                new ChatThread(socket).start();
                this.nbClients = this.addClient(socket);
                
                System.out.println("Nombre de clients co : " + this.getNbClients());

            }
        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("Erreur : " + ex);
        }
    }

   

    /**
     * Ajoute un client au vecteur
     *
     * @param out
     * @return
     */
    synchronized int addClient(Socket socketClient) {
        this.nbClients++; // New Client
        this.tabClients.addElement(socketClient);
        int nbClient = this.tabClients.size() - 1;
        return nbClient;

    }

    

    synchronized public int getNbClients() {
        return this.nbClients; // retourne le nombre de clients connectés
    }

    synchronized Vector getListClient() {
        return this.tabClients;
    }

    public static void main(String[] args) {
        ChatServeur serveur = new ChatServeur();
    }

    @Override
    public void run() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    


}
