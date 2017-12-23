package serveur;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.UUID;
import java.util.HashMap;

import commun.ClientInfo;
import commun.DejaConnecteException;
import commun.Objet;
import commun.PasCreateurException;
import commun.PseudoDejaUtiliseException;
import commun.SalleDeVente;
import commun.Message;

public interface IHotelDesVentes extends Remote {
	
	public HashMap<UUID, String> getMapSalles() throws RemoteException;

	UUID creerSalle(ClientInfo client, Objet o, String nomDeSalle) throws RemoteException;

	HashMap<UUID, Objet> login(ClientInfo myClientInfos) throws RemoteException, PseudoDejaUtiliseException, DejaConnecteException;

	void logout(ClientInfo client) throws RemoteException;

	void encherir(int prix, UUID clientId, UUID idSDV) throws RemoteException;

	Objet getObjetEnVente(UUID idSDV) throws RemoteException;

	void ajouterObjet(Objet objetAVendre, UUID idSDV, UUID client) throws RemoteException, PasCreateurException;

	public Objet rejoindreSalle(UUID roomId, ClientInfo clientId) throws RemoteException;

	public SalleDeVente getSalleById(UUID roomId) throws RemoteException;
	
	public void supprimerSDV (UUID roomID) throws RemoteException;
	
	public void posterMessage (UUID idSalle, Message message) throws RemoteException;
	
	public void fermerSalle (UUID idSalle, UUID idClient) throws PasCreateurException;
	
}
