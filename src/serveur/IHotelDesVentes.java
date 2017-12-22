package serveur;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.UUID;

import commun.ClientInfo;
import commun.DejaConnecteException;
import commun.InfoSalleDeVente;
import commun.Objet;
import commun.PseudoDejaUtiliseException;

public interface IHotelDesVentes extends Remote {
	
	public ArrayList<InfoSalleDeVente> getListeSalles() throws RemoteException;

	UUID creerSalle(ClientInfo client, Objet o, String nomDeSalle) throws RemoteException;

	void login(UUID id, String nomUtilisateur) throws RemoteException, PseudoDejaUtiliseException, DejaConnecteException;

	void logout(ClientInfo client) throws RemoteException;

	void rencherir(int prix, UUID clientId, UUID idSDV) throws RemoteException;

	Objet getObjetEnVente(UUID idSDV) throws RemoteException;

	void ajouterObjet(Objet objetAVendre, UUID idSDV) throws RemoteException;

	public Objet rejoindreSalle(UUID roomId, ClientInfo clientId) throws RemoteException;

	public SalleDeVente getSalleById(UUID roomId);
}
