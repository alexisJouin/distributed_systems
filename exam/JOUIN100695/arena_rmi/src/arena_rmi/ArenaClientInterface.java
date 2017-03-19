package arena_rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author Alexis
 */
public interface ArenaClientInterface extends Remote {

    public void sendClient() throws RemoteException;

    public void sendNbPlaces(int nbPlaces) throws RemoteException;

    public String getNom() throws RemoteException;

}
