package rmi_common;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class Conversation {

    private int id = 0;
    private String type;
    private ArrayList<ClientInterface> tabClientsInterface = new ArrayList<ClientInterface>();
    private ArrayList<String> tabClientsName = new ArrayList<String>();
    private ArrayList<String> tabMessages = new ArrayList<String>();

    //Pour la première instance quand on a nouveau connecté
    public Conversation() throws RemoteException {
        id++;
        type = "forAll";
    }

    public void addClient(ClientInterface client) {
        tabClientsInterface.add(client);
    }

    public void addMessage(String message) {
        tabMessages.add(message);
    }

    /**
     * GETTERS
     *
     * @return
     */
    public int getId() {
        return id;
    }

    public ArrayList<ClientInterface> getTabClientsInterface() {
        return tabClientsInterface;
    }

    public ArrayList<String> getTabClientsName() {
        return tabClientsName;
    }

    public ArrayList<String> getTabMessages() {
        return tabMessages;
    }

}
