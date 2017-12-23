package client;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.UUID;
import commun.*;

public interface IClient extends Remote {
	
	public void nouveauMessage (UUID idSalle, Message message) throws RemoteException;
	
	public void notifModifObjet (UUID idSalle, Objet objet);
	
	public void notifFermetureSalle (UUID idSalle);

}