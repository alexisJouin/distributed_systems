package tp_chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class ChatClient extends Thread {
	Socket socket;
	private String host = "localhost";
	private int port = ChatServeur.PORT;
	private String name;

	public ChatClient() {

		// Démarrage de l'interface
		ChatInterface chat = new ChatInterface(this);
		chat.run();

		// Saisie du nom
		this.name= JOptionPane.showInputDialog("Saisir votre Pseudo : ");		

		try {
			socket = new Socket(host, port);
			PrintWriter writer = new PrintWriter(socket.getOutputStream());
			writer.println(this.name);
			writer.flush();
			
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void sendMessage(String message) {
		try {

			PrintWriter writer = new PrintWriter(socket.getOutputStream());

			writer.println(message);
			writer.flush();

			InputStream stream = socket.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
			

		} catch (UnknownHostException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getNameClient() {
		return this.name;
	}

	public static void main(String[] args) {

		new ChatClient();
		
		//socket.close(); => A mettre quelque part mais où ? 
	}

}
