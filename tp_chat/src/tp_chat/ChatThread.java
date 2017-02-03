package tp_chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Vector;

public class ChatThread extends Thread implements Runnable {

    private Socket socket;
    private PrintWriter sortie;
    private BufferedReader entree;
    private int numClient = 0;
    private String clientName;
    private ArrayList tabInfoMsg = new ArrayList();
    static ArrayList<String> tabNomClients = new ArrayList<String>();

    public ChatThread(Socket s) {

        this.socket = s;
        try {

            this.sortie = new PrintWriter(this.socket.getOutputStream());
            this.entree = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            //this.numClient = serveur.addClient(this.sortie);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //ENVOIE A TOUT LE MONDE
    public void sendToAll(String message, String clientName) throws IOException {
        for (Socket s : ChatServeur.tabClients) {
            PrintWriter out = new PrintWriter(s.getOutputStream());
            tabInfoMsg.add(message);
            tabInfoMsg.add(this.clientName);
            out.println("msg:" + tabInfoMsg);
            out.flush();
            tabInfoMsg.clear();
        }
    }

    public void sendToAll(ArrayList clientCoName) throws IOException {
        for (Socket s : ChatServeur.tabClients) {
            PrintWriter out = new PrintWriter(s.getOutputStream());
            out.println("listCo:" + clientCoName);
            out.flush();
        }
    }

    public void run() {
        String message = "";
        this.numClient = ChatServeur.tabClients.size();
        System.out.println("Un nouveau client s'est connecte, n° " + this.numClient++);
        try {

            String line = "";
            int i = 0;
            this.clientName = entree.readLine();
            System.out.println("Nom du client : " + this.clientName);
            tabNomClients.add(this.clientName);

            sendToAll(tabNomClients);//Envoi la liste des clients connectés

            //Envoi des messages
            while ((line = entree.readLine()) != null) {
                this.sendToAll(line, clientName);
                System.out.println("MSG de " + this.clientName + " n° " + ++i + " : " + line);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally { //Déconnection du client ??
            try {
                System.out.println("Le client n° " + this.numClient + " s'est deconnecte");
                ChatServeur.tabClients.remove(this.socket);
                this.tabInfoMsg.remove(this.socket);
                this.socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
