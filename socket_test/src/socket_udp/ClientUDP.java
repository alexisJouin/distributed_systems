package socket_udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.rmi.UnknownHostException;

public class ClientUDP {
	private String host = "localhost";

	public ClientUDP()
	{
		try{
		InetAddress address = InetAddress.getByName(host);
		DatagramSocket socket = new DatagramSocket();
		
		byte[] buffer = "Bonjour".getBytes();
		DatagramPacket sendPacket = new DatagramPacket(buffer, buffer.length, address, ServeurUDP.PORT);
		
		buffer = new byte[256];
		DatagramPacket receivePacket = new DatagramPacket(buffer, buffer.length, address, ServeurUDP.PORT);
		
		socket.send(sendPacket);
		
		System.out.println("Client en attente");
		socket.receive(receivePacket);
		System.out.println(new String(receivePacket.getData()));
		
		socket.close();
		
		}catch(UnknownHostException e){
			e.printStackTrace();
		}catch(SocketException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new ClientUDP();

	}

}
