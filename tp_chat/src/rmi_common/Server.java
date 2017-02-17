package rmi_common;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class Server implements ServerInterface {

    private ClientInterface client;
    static ArrayList<String> tabClients = new ArrayList<String>();

    public Server() {
        try {
            String name = "server";
            ServerInterface stub = (ServerInterface) UnicastRemoteObject.exportObject(this, 0);
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind(name, stub);
            System.out.println("Server bound");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sayHello() throws RemoteException {
        System.out.println("Hello to you !");
    }

    @Override
    public void register(ClientInterface client) throws RemoteException {
        this.client = client;
        client.showYourName();
        tabClients.add(client.getName());
        System.out.println("Personnes connectées : " + tabClients);
    }

    @Override
    public void getMessage(String from, String message) throws RemoteException {
        System.out.println("Message reçus : " + message + " de : " + from);

    }

    @Override
    public void sendToAll(String from, String message) throws RemoteException {
        System.out.println("Message reçus : " + message + " de : " + from);
    }

    @Override
    public void getName(String name) throws RemoteException {

    }

    public static void main(String[] args) {
        Server server = new Server();
    }
}
