package chat_impl;

import chat.ChatClient;
import chat.ChatServerPOA;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Vector;
import org.omg.CORBA.ORB;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

public class ChatServerImpl extends ChatServerPOA {

    ORB orb;
    ChatClient client;
    int idClient = 1;

    static ArrayList<ChatClient> clients = new ArrayList<ChatClient>();

    public ChatServerImpl(String[] args) {
        try {
            orb = ORB.init(args, null);
            Thread orbThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    orb.run();
                }
            });

            orbThread.start();

            //Mise à disposition de l'objet CORBA SERVEUR
            POA rootPOA = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
            rootPOA.the_POAManager().activate();
            byte[] id = rootPOA.activate_object(this);

            //Ecriture de l'IOR du serveur dans un fichier (a destination des clients)
            org.omg.CORBA.Object ref = rootPOA.id_to_reference(id);
            String ior = orb.object_to_string(ref);
            System.out.println(ior);
            PrintWriter file = new PrintWriter("server_ior.txt");
            file.println(ior);
            file.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void register(ChatClient c) {
        this.client = c;
        c.id(idClient);
        System.out.println("Enregistrement de " + client.nom() + " n° " + client.id());
        this.clients.add(c);
        this.idClient++;
        this.setListConnect();
    }

    @Override
    public void sendToAll(ChatClient from, String msg) {
        for (ChatClient client : clients) {
            client.setMessage(from.nom(), msg);
        }
    }

    public static void main(String[] args) {
        new ChatServerImpl(args);
    }

    @Override
    public void supprimerClient(ChatClient client) {
        this.clients.remove(client);
        System.out.println("Client n° " + client.id() + ", " + client.nom() + " déconnecté !");       
        this.setListConnect();
    }
    
    
    public void setListConnect(){
        //Conversion ArrayList en Array Simple
        ChatClient[] tabClient = new ChatClient[clients.size()];
        tabClient = clients.toArray(tabClient);
        //Pour tous les clients on envoi la liste des connectés 
        for (ChatClient client : clients) {
            client.setListCo(tabClient);
        }
    }

}
