package tp_chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;


public class ChatClient {

    Socket socket;
    private String host = "localhost";
    private int port = ChatServeur.PORT;
    private String name;
    private ChatInterface chat;
    private JLabel nomClient;
    
    private DefaultListModel<String> model;
    private JList<String> listCo;
    private ArrayList<String> tabClients;

    public ChatClient() {

        // Démarrage de l'interface
        this.chat = new ChatInterface(this);
        this.chat.run();

        // Saisie du nom
        this.name = JOptionPane.showInputDialog("Saisir votre Pseudo : ");
        //Affichage sur l'interface
        nomClient = chat.getNomClient();
        nomClient.setText(this.name + ": ");

        System.out.println("Bienvenue Client " + this.name);

        try {
            socket = new Socket(host, port);
            PrintWriter writer = new PrintWriter(socket.getOutputStream());
            writer.println(this.name);
            writer.flush();

            //Get serveur message
            InputStream is;
            is = socket.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);

//            String nomsCo = br.readLine();
//            listCo.append(nomsCo);
            String message = "";
            while ((message = br.readLine()) != null) {
                //Si c'est un message
                if (message.contains("msg:")) {
                    List<String> arrayMsg = new ArrayList<String>(Arrays.asList(message.split(",")));
                    String msg = arrayMsg.get(0).replace("msg:[", "");
                    String client = arrayMsg.get(1).replace("]", "");
                    System.out.println("Message du serveur : " + msg);
                    JTextArea listMsgs = chat.getMsgs();
                    //Affichage dans la fenêtre
                    listMsgs.append("\n " + client + " > " + msg);
                    //Si c'est pour un nouveu client
                } else if (message.contains("listCo:")) {
                    List<String> arrayClientsCo = new ArrayList<String>(Arrays.asList(message.split(",")));
                    
                    this.listCo = chat.getListCo();
                    this.model = chat.getModel();
                    for (String clientsCo : arrayClientsCo) {
                        //listCo.append("\n" + clientsCo.replace("listCo:[", "").replace("]", ""));
                        model.addElement(clientsCo.replace("listCo:[", "").replace("]", ""));
                        chat.setModel(model);
                        System.out.println("list co : " + clientsCo);
                    }

                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //Closing the socket
            try {
                socket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public void sendMessage(String message) {
        try {

            PrintWriter writer = new PrintWriter(socket.getOutputStream());
            System.out.println("Test envoie depuis client : " + message);
            writer.println(message);
            writer.flush();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getNameClient() {
        return this.name;
    }

    public static void main(String[] args) {

        ChatClient chatClient = new ChatClient();
    }

}
