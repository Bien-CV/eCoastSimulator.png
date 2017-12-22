//Manipulé uniquement par HotelDesVentes.java
//Manque :
//ajouterObjet
//getObjet
package serveur;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import commun.Objet;

public class SalleDeVente extends UnicastRemoteObject {
	static final int INDEX_PREMIER_OBJET = 0;

	private static final long serialVersionUID = 1L;
	private UUID id;
	private List<ClientInfo> acheteurs = new ArrayList<ClientInfo>();
	private List<Objet> objetsEnVente = new ArrayList<Objet>();
	private List<Objet> objetsVendus = new ArrayList<Objet>();
	private String nom;
	
	protected SalleDeVente(Objet o) throws RemoteException {
		super();
		objetsEnVente.add(o);
		// TODO Auto-generated constructor stub
	}

	public void ajouterObjet(Objet objet){
		objetsEnVente.add(objet);
	}
	
	public void ajouterClient(ClientInfo client) {
		acheteurs.add(client);
	}

	public Objet getObjet(){
		// TODO Auto-generated method stub
		return null;
	}
	
	public Objet getObjetCourant() {
		return objetsEnVente.get(INDEX_PREMIER_OBJET);
	}

	public List<Objet> getListeObjets(){
		return objetsEnVente;
	}

	public List<ClientInfo> getListeAcheteurs(){
		// TODO Auto-generated method stub
		return acheteurs;
	}

	public UUID getId() {
		// TODO Auto-generated method stub
		return id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}
	
	public void venteSuivante() {
		objetsVendus.add(getObjetCourant());
		objetsEnVente.remove(INDEX_PREMIER_OBJET);
	}

}
