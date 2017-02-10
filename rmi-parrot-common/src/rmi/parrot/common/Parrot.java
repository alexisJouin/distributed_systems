package rmi.parrot.common;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;


public interface Parrot extends Remote {


    public void repete(String msg) throws RemoteException;
    public Date quelleHeureEstIl() throws RemoteException;
    
}
