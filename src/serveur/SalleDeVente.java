//Manipulé uniquement par HotelDesVentes.java
//Cas non-géré : Un objet invendu à la fin de son timer 
//Manque :
//ajouterObjet
//getObjet
//Initialiser la date de mise en vente de l'objet lors de sa mise en vente

package serveur;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import commun.DejaDansLaSalleException;
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
		debutVente();
	}

	public void ajouterObjet(Objet objet){
		objetsEnVente.add(objet);
	}
	
	public void ajouterClient(ClientInfo client) throws DejaDansLaSalleException {
		if (acheteurs.contains(client)) {
			throw new DejaDansLaSalleException();
		}
		else acheteurs.add(client);
	}
	
	public Objet getObjetCourant() {
		return objetsEnVente.get(INDEX_PREMIER_OBJET);
	}

	public List<Objet> getListeObjets(){
		return objetsEnVente;
	}

	public List<ClientInfo> getListeAcheteurs(){
		return acheteurs;
	}

	public UUID getId() {
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
		debutVente();
	}
	
	public void debutVente() {
		getObjetCourant().miseEnVente();
	}
	
	public void chronoVente() {
		sleep(30000);
	}

}
