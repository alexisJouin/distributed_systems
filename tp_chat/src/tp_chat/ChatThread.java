package tp_chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class ChatThread extends Thread implements Runnable {

    private Socket socket;
    private PrintWriter sortie;
    private BufferedReader entree;
    private int numClient = 0;
    private Client client;
    private ArrayList<Client> clients;
    private String clientName;
    private ArrayList tabInfoMsg = new ArrayList();
    private ArrayList<String> tabNomClients = new ArrayList<String>();

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

    public void run() {
        try {

            String line = "";
            int i = 0;

            addClient(socket, entree.readLine());//"Création du client"
            sendToAll(clients);//Envoi la liste des clients connectés

            //Envoi des messages
            while ((line = entree.readLine()) != null) {
                this.sendToAll(line, client.getName());
                System.out.println("MSG de " + client.getName() + " n° " + ++i + " : " + line);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally { //Déconnection du client ??
            try {
                System.out.println("Le client n° " + this.client.getNumero() + " s'est deconnecte");
                this.clients.remove(this.numClient);
                this.socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //Gestion du client
    public void addClient(Socket socketClient, String nameClient) {
        client = new Client();
        client.setSocket(socketClient);
        client.setName(nameClient);
        client.setNumero(this.clients.size() + 1);

        System.out.println("-- -- -- -- --");
        System.out.println("Un nouveau client s'est connecte, n° " + client.getNumero() + " n°Socket : " + client.getSocket());
        System.out.println("Nom du client : " + client.getName());
        System.out.println("-- -- -- -- --");

        clients.add(client);

    }

    //ENVOIE A TOUT LE MONDE
    public void sendToAll(String message, String clientName) throws IOException {
        for (Client c : clients) {
            PrintWriter out = new PrintWriter(c.getSocket().getOutputStream());
            tabInfoMsg.add(message);
            tabInfoMsg.add(c.getName());
            out.println("msg:" + tabInfoMsg);
            out.flush();
            tabInfoMsg.clear();
        }
    }

    //ENVOIE A TOUT LE MONDE LE NOM DES CLIENTS CO
    public void sendToAll(ArrayList clientCoName) throws IOException {
        for (Client c : clients) {
            PrintWriter out = new PrintWriter(c.getSocket().getOutputStream());
            out.println("listCo:" + clientCoName);
            out.flush();
        }
    }

}
