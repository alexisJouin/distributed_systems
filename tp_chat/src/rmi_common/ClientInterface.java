package rmi_common;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface ClientInterface extends Remote {

    public void showYourName() throws RemoteException;

    public void sendMessage(String name, String message) throws RemoteException;

    public String getName() throws RemoteException;

    public void setListCo(ArrayList<String> listClient) throws RemoteException;

    public void setMessage(String client, String message) throws RemoteException;

    public void sendConversation(Conversation conversation) throws RemoteException;

}
