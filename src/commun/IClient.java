package commun;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.UUID;

public interface IClient extends Remote {
	
	public void nouveauMessage (UUID idSalle, Message message) throws RemoteException;
	
	public void notifModifObjet (UUID idSalle, Objet objet) throws RemoteException;
	
	public void notifFermetureSalle (UUID idSalle) throws RemoteException;
	
	public void notifNouvelleSalle (SalleDeVenteInfo sdvi) throws RemoteException;

}