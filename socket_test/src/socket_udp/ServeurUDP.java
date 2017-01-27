package socket_udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class ServeurUDP {
	static final int PORT = 6666;

	public ServeurUDP() {
		try {

			DatagramSocket socket = new DatagramSocket(PORT);

			byte[] sendbuffer = null;
			byte[] receivebuffer = new byte[256];

			DatagramPacket receivePacket = new DatagramPacket(receivebuffer, receivebuffer.length);
			DatagramPacket sendPacket = null;

			while (true) {
				System.out.println("Serveur en attente");
				socket.receive(receivePacket);
				System.out.println(new String(receivePacket.getData()));

				InetAddress address = receivePacket.getAddress();
				int port = receivePacket.getPort();
				sendbuffer = ("Vous etes connecte depuis la machine : " + address + " et sur le port " + port)
						.getBytes();
				sendPacket = new DatagramPacket(sendbuffer, sendbuffer.length, address, port);
				socket.send(sendPacket);

			}

		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String args[]) {
		new ServeurUDP();
	}

}
