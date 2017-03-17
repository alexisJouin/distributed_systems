package arena_rmi;

import java.rmi.Remote;

/**
 *
 * @author Alexis
 */
public interface ArenaClientInterface extends Remote {
    public void sendClient(String nom);
    public void sendNbPlaces(int nbPlaces);
    
}
