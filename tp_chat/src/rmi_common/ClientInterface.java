package rmi_common;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientInterface extends Remote {
    public void showYourName() throws RemoteException;
    public void sendMessage(String name, String message) throws RemoteException;
    public String getName() throws RemoteException;
}
