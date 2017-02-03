package tp_chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

public class ChatClient{

    Socket socket;
    private String host = "localhost";
    private int port = ChatServeur.PORT;
    private String name;
    private ChatInterface chat;

    public ChatClient() {

        // Démarrage de l'interface
        this.chat = new ChatInterface(this);
        this.chat.run();

        // Saisie du nom
        this.name = JOptionPane.showInputDialog("Saisir votre Pseudo : ");

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
            String message = "";
            while ((message = br.readLine()) != null) {
                System.out.println("Message du serveur : " + message);
                
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
