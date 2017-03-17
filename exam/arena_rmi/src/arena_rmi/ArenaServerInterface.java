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
    public Vector<Socket> getClients() throws RemoteException;
    public HashMap<String, Integer> getReservations() throws RemoteException;
    int getCapacity() throws RemoteException;
}
