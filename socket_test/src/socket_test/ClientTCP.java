package socket_test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Date;


public class ClientTCP {
	Socket socket;
	private String host = "localhost";
	private int port = ServeurTCP.PORT;

	public ClientTCP() {
		try {
			socket = new Socket(host, port);
			PrintWriter writer = new PrintWriter(socket.getOutputStream());
			writer.println("Salut !!");
			writer.flush();

			writer.println(ServeurTCP.END_OF_MESSAGE);
			writer.flush();

			InputStream stream = socket.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
			String line = "";
			while ((line = reader.readLine()) != null) {
				System.out.println(new Date() + " -> Reçu du serveur " + " : " + line);
			}

			socket.close();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new ClientTCP();
	}

}
