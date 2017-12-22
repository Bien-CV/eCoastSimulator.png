package serveur;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.UUID;

import commun.InfoSalleDeVente;
import commun.Objet;

public interface IHotelDesVentes extends Remote {
	
	public ArrayList<InfoSalleDeVente> getListeSalles() throws RemoteException;

	UUID creerSalle(ClientInfo client, Objet o, String nomDeSalle) throws RemoteException;

	boolean login(UUID id, String nomUtilisateur) throws RemoteException;

	void logout(ClientInfo client);

	void rencherir(int prix, UUID clientId, UUID idSDV) throws RemoteException;

	Objet getObjetEnVente(UUID idSDV) throws RemoteException;

	void ajouterObjet(Objet objetAVendre, UUID idSDV) throws RemoteException;

	public Objet rejoindreSalle(UUID roomId, ClientInfo clientId) throws RemoteException;

	public void renommerSalle(UUID roomId, ClientInfo client, String nomDeSalle) throws RemoteException;
	
	public SalleDeVente getSalleById(UUID roomId);
}
