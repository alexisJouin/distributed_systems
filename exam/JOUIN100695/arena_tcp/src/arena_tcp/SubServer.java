package arena_tcp;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Vector;

/**
 *
 * @author Alexis Jouin
 */
public class SubServer extends Thread {

    ArenaServer server;
    Socket socket;
    private String client;
    private int nbPlaces;
    private Vector<Socket> clients = new Vector<Socket>();
    private HashMap<String, Integer> reservations = new HashMap<String, Integer>();

    //Constructeur par défaut
    public SubServer(ArenaServer server, Socket socket) {
        this.server = server;
        this.socket = socket;
        this.client = "Toto"; // Default name
        this.nbPlaces = 0; //Default nbplaces
        this.clients.add(socket);
    }

    public void run() {
        try {
            InputStream stream = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    stream));
            PrintWriter writer = new PrintWriter(socket.getOutputStream());

            this.nbPlaces = server.getCapacity();
            this.clients = server.getClients();
            this.reservations = server.getReservations();

            writer.println(this.nbPlaces);

            String client = "";
            int nbPlaces;

            //server.addReservation(this.client, this.nbPlaces);
            while ((client = reader.readLine()) != null) {

                nbPlaces = Integer.parseInt(reader.readLine());

                this.reservations.put(client, nbPlaces);
                this.nbPlaces = this.nbPlaces - nbPlaces;

                System.out.println("Client : " + client);
                System.out.println("NB PLACES  : " + nbPlaces);
                
                writer.println(Integer.toString(this.nbPlaces));
                
                writer.flush();
            }

        } catch (Exception Ex) {
            Ex.printStackTrace();
        }
    }

}
