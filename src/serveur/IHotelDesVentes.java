package serveur;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.UUID;

public interface IHotelDesVentes extends Remote {

	public List<SalleDeVente> getListeSalles() throws RemoteException;

	//public boolean inscriptionAcheteur(String pseudo, ClientInfo client);

	SalleDeVente rejoindreSalle(UUID roomId, UUID clientId) throws RemoteException;

	SalleDeVente creerSalle(ClientInfo client, Objet o) throws RemoteException;

	boolean login(UUID id, String nomUtilisateur) throws RemoteException;

	void logout(ClientInfo client);

	void ajouterObjet(Objet objetAVendre, SalleDeVente sdv)throws RemoteException;

	Objet getObjetEnVente(SalleDeVente sdv) throws RemoteException;

	void rencherir(int prix, ClientInfo client, SalleDeVente sdv) throws RemoteException;

}
