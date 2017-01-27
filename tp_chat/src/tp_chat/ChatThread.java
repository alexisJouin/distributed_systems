package tp_chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatThread extends Thread implements Runnable {

	private Thread thread;
	private Socket socket;
	private PrintWriter sortie;
	private BufferedReader entree;
	private ChatServeur serveur;
	private int numClient = 0;

	ChatThread(Socket s, ChatServeur serveur) {

		this.serveur = serveur;
		this.socket = s;

		try {

			this.sortie = new PrintWriter(this.socket.getOutputStream());
			this.entree = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			this.numClient = serveur.addClient(this.sortie);

		} catch (IOException e) {
			e.printStackTrace();
		}

		thread = new Thread(this);
		thread.start();
	}

	public void run() {
		String message = "";
		System.out.println("Un nouveau client s'est connecte, no " + this.numClient);
		try {

			char charCur[] = new char[1];
			while (this.entree.read(charCur, 0, 1) != -1) {
				if (charCur[0] != '\u0000' && charCur[0] != '\n' && charCur[0] != '\r')
					message += charCur[0];
				else if (!message.equalsIgnoreCase("")) {
					if (charCur[0] == '\u0000')
						this.serveur.sendToAll(message, "" + charCur[0]);
					else
						this.serveur.sendToAll(message, "");
					message = "";
				}
			}
		} catch (Exception e) {
		} finally // ?Déco du client
		{
			try {
				System.out.println("Le client no " + this.numClient + " s'est deconnecte");
				this.serveur.delClient(this.numClient); 
				this.socket.close(); 
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
