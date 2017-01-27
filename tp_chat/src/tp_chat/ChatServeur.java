package tp_chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class ChatServeur {
	static final int PORT = 6666;
	private int nbClients = 0;
	private Vector<PrintWriter> tabClients = new Vector<PrintWriter>();
	private PrintWriter sortie;
	private BufferedReader entree;
	private Socket socket;
	private String clientName;

	public ChatServeur() {
		try {
			ServerSocket serverSocket = new ServerSocket(PORT);

			while (true) {
				System.out.println("Serveur Prêt");
				socket = serverSocket.accept();

				sortie = new PrintWriter(socket.getOutputStream());
				entree = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				this.nbClients = this.addClient(sortie);

				System.out.println("Liste des clients : " + this.getListClient());
				
				new ChatThread(socket, this);
				
				String line = "";
				int i = 0;
				this.clientName = entree.readLine();
				System.out.println("Nom du client : "+this.clientName);
				while ((line = entree.readLine()) != null) {
					System.out.println("MSG " + ++i + " : " + line);
				}

				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				socket.close();
			}
		} catch (IOException ex) {
			ex.printStackTrace();
			System.out.println("Erreur : " + ex);
		}
	}


	public void sendToAll(String message, String last) {
		PrintWriter out;
		for (int i = 0; i < this.tabClients.size(); i++) // parcours de la table
															// des clients
															// connectés
		{
			out = (PrintWriter) this.tabClients.elementAt(i);
			if (out != null) {
				out.print(message);
				// out.print(message+last);
				out.flush();
			}
		}

	}

	/**
	 * Ajoute un client au tableau
	 * 
	 * @param out
	 * @return
	 */
	public int addClient(PrintWriter out) {
		this.nbClients++; // New Client
		this.tabClients.addElement(out);
		int nbClient = this.tabClients.size() -1;
		System.out.println("Nombre de clients co : " + nbClient);
		return nbClient;

	}

	/**
	 * Supprime un client du tableau
	 * 
	 * @param i
	 */
	public void delClient(int i) {
		this.nbClients--;
		if (this.tabClients.elementAt(i) != null) {
			this.tabClients.removeElementAt(i);
		}
	}

	public Vector getListClient() {
		return this.tabClients;
	}

	public static void main(String[] args) {
		ChatServeur serveur = new ChatServeur();
	}

}
