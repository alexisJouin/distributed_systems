package socket_test;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

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

			writer.println("Ok ...");
			writer.flush();

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
