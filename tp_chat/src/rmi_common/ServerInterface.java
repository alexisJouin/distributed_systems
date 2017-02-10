package rmi_common;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerInterface extends Remote {

    public void sayHello() throws RemoteException;

    public void register(ClientInterface client) throws RemoteException;

}
