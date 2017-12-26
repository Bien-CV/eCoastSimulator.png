package commun;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.UUID;

public interface IClient extends Remote {
	
	public void nouveauMessage (UUID idSalle, Message message) throws RemoteException;
	
	public void notifModifObjet (UUID idSalle, Objet objet);
	
	public void notifFermetureSalle (UUID idSalle);
	
	public void notifNouvelleSalle (UUID idSalle, Objet objEnVente);

}