package rmi_common;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerInterface extends Remote {

    public void sayHello() throws RemoteException;

    public void register(ClientInterface client) throws RemoteException;

    public void getMessage(String from, String message) throws RemoteException;

    public void sendToAll(String from, String message) throws RemoteException;

}
