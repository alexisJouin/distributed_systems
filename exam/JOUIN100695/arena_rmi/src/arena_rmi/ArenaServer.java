package arena_rmi;

import java.net.Socket;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Vector;

public class ArenaServer implements ArenaServerInterface {

    public static final int PORT = 6667;

    private Vector<ArenaClientInterface> clients = new Vector<ArenaClientInterface>();
    private HashMap<String, Integer> reservations = new HashMap<String, Integer>();
    private int capacity = 5000;
    private int nbPlaces;

    public ArenaServer() {
        try {
            String name = "server";
            ArenaServerInterface stub = (ArenaServerInterface) UnicastRemoteObject.exportObject(this, 0);
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind(name, stub);

            System.out.println("Server bound");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Vector<ArenaClientInterface> getClients() {
        return clients;
    }

    @Override
    public HashMap<String, Integer> getReservations() {
        return reservations;
    }

    @Override
    public int getCapacity() {
        return capacity;
    }

    public static void main(String[] args) {
        new ArenaServer();
    }

    @Override
    public void registry(ArenaClientInterface client) throws RemoteException {
        this.clients.add(client);
        this.reservations.put(client.getNom(), this.nbPlaces);
    }

    @Override
    public void setNbPlaces(int nbPlaces) throws RemoteException {
        this.nbPlaces = nbPlaces;
        this.capacity = this.capacity - nbPlaces;
    }
}
