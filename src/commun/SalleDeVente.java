//Manipulé uniquement par HotelDesVentes.java

package commun;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SalleDeVente extends UnicastRemoteObject {
	static final int INDEX_PREMIER_OBJET = 0;

	private static final long serialVersionUID = 1L;
	private UUID id;
	private List<ClientInfo> acheteurs = new ArrayList<ClientInfo>();
	private List<Objet> objetsEnVente = new ArrayList<Objet>();
	private List<Objet> objetsVendus = new ArrayList<Objet>();
	private List<Message> listeMessages = new ArrayList<Message>();
	private String nom;
	private UUID idCreateur;
	
	public SalleDeVente(Objet o, String n, UUID createur) throws RemoteException {
		super();
		this.nom = n;
		idCreateur = createur;
		objetsEnVente.add(o);
		debutVente();
		id = UUID.randomUUID();
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
	
	public void retirerClient(ClientInfo client) {
		acheteurs.remove(client);
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
	
	public void venteSuivante() throws PlusDeVenteException {
		objetsVendus.add(getObjetCourant());
		objetsEnVente.remove(INDEX_PREMIER_OBJET);
		if (objetsEnVente.size() > 0) {
			debutVente();
		}
		else {
			throw new PlusDeVenteException();
		}
	}
	
	public void debutVente() {
		getObjetCourant().miseEnVente();
	}
	
	public int nombreAcheteurs() {
		return acheteurs.size();
	}
	
	public void nouveauMessage (Message message) {
		listeMessages.add(message);
	}
	
	public List<Message> getListeMessages() {
		return listeMessages;
	}

	public UUID getIdCreateur() {
		return idCreateur;
	}

}
