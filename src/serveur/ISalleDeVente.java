package serveur;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import client.Acheteur;
import commun.Objet;

public interface ISalleDeVente extends Remote {
	
	/**
	 * Augmente le prix de l'objet a vendre.
	 * @param nouveauPrix le nouveau prix que le client a donne
	 * @param acheteur l'acheteur ayant encherit 
	 * @return le nouveau prix de l'objet a vendre
	 * @throws RemoteException
	 */
	public int rencherir(int nouveauPrix, Acheteur acheteur) throws RemoteException, Exception;
	

	/**
	 * Methode permettant d ajouter un objet aux encheres.
	 * @param objet l'objet a vendre.
	 * @throws RemoteException
	 */
	public void ajouterObjet(Objet objet) throws RemoteException;
	
	
	public Objet getObjet() throws RemoteException;
	
	public List<Objet> getListeObjets() throws RemoteException;
	
	public List<ClientInfo> getListeAcheteurs() throws RemoteException;


}
