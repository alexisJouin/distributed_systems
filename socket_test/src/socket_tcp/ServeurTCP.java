package socket_tcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServeurTCP extends Thread {
	static final int PORT = 6666;
	static final String END_OF_MESSAGE = "END_OF_MESSAGE";

	public ServeurTCP() {
		try {

			ServerSocket serverSocket = new ServerSocket(PORT);
			int nbClients = 0;
			while (true) {

				System.out.println("Serveur Prêt");
				Socket socket = serverSocket.accept();
				nbClients++;
				System.out.println("Client " + nbClients + " Connecté");
				InputStream stream = socket.getInputStream();
				BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
				String line = "";
				int i = 0;
				while ((line = reader.readLine()).compareTo(END_OF_MESSAGE) != 0) {
					System.out.println("MSG " + ++i + " : " + line);
				}

				PrintWriter writer = new PrintWriter(socket.getOutputStream());
				writer.println("Bye !");
				writer.flush();

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

	public static void main(String[] args) {
		new ServeurTCP();
	}
}
