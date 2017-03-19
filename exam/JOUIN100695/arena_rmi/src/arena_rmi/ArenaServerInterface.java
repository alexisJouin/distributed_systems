package arena_rmi;

import java.net.Socket;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Vector;

/**
 *
 * @author Alexis
 */
public interface ArenaServerInterface extends Remote {
    public Vector<ArenaClientInterface> getClients() throws RemoteException;
    public HashMap<String, Integer> getReservations() throws RemoteException;
    public int getCapacity() throws RemoteException;
    public void registry(ArenaClientInterface client) throws RemoteException;
    public void setNbPlaces(int nbPlaces) throws RemoteException;
}
