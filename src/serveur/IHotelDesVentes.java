package serveur;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.UUID;

public interface IHotelDesVentes extends Remote {

	public List<SalleDeVente> getListeSalles() throws RemoteException;
	
	public String getListeTextuelleDesSalles() throws RemoteException;

	boolean rejoindreSalle(UUID roomId, UUID clientId) throws RemoteException;

	SalleDeVente creerSalle(ClientInfo client, Objet o) throws RemoteException;

	boolean login(UUID id, String nomUtilisateur) throws RemoteException;

	void logout(ClientInfo client);

	void rencherir(int prix, UUID clientId, UUID idSDV) throws RemoteException;

	Objet getObjetEnVente(UUID idSDV) throws RemoteException;

	void ajouterObjet(Objet objetAVendre, UUID idSDV) throws RemoteException;

}
