package client;

import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.UUID;
import commun.*;
import serveur.IHotelDesVentes;

public interface IClient extends Remote {
	
	void fermetureSalle (UUID idSDV) throws RemoteException;
	
	public void notifyNouvelleEnchere (float nouveauPrix, String gagnant, UUID idObjet) throws RemoteException;
	
	void notifyNouvelleVente (Objet nouvelObjet, UUID idSalle) throws RemoteException;
	
	public void nouveauMessage (UUID idSalle, String pseudo, String message) throws RemoteException;
	
	void connexionClient();
}