package serveur;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.UUID;

public interface IHotelDesVentes extends Remote {
	
	public String getListeTextuelleDesSalles() throws RemoteException;

	SalleDeVente creerSalle(ClientInfo client, Objet o) throws RemoteException;

	boolean login(UUID id, String nomUtilisateur) throws RemoteException;

	void logout(ClientInfo client);

	void rencherir(int prix, UUID clientId, UUID idSDV) throws RemoteException;

	Objet getObjetEnVente(UUID idSDV) throws RemoteException;

	void ajouterObjet(Objet objetAVendre, UUID idSDV) throws RemoteException;

	boolean rejoindreSalle(UUID roomId, ClientInfo clientId) throws RemoteException;

}
