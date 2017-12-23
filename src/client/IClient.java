package client;

import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.UUID;
import commun.*;
import serveur.IHotelDesVentes;

public interface IClient extends Remote {
	
	void fermetureSalle (UUID idSDV) throws RemoteException;
	
	public void nouveauMessage (UUID idSalle, Message message) throws RemoteException;
	
	public void notifModifObjet (UUID idSalle, Objet objet);

}