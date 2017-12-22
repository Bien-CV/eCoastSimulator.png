// TODO :
// notifyNouvelleEnchere
// notifyNouvelleVente

package client;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.UUID;
import java.util.HashMap;

import commun.Objet;
import serveur.ClientInfo;
import serveur.IHotelDesVentes;

public class Client extends UnicastRemoteObject implements IClient {

	private static final long serialVersionUID = 1L;
	private static final String adresseServeur = "localhost:8090/hoteldesventes";

	private String pseudo;
	private IHotelDesVentes hdv;
	private HashMap<UUID, Objet> ventesSuivies;
	private UUID id;
	

	public Client(String pseudo) throws RemoteException {
		super();
		this.pseudo = pseudo;
		this.hdv = connexionServeur();
		this.ventesSuivies = new HashMap<UUID, Objet>();
		//TODO:init id
		
		//this.currentObjet = hdv.getObjetEnVente();
	}

	public static IHotelDesVentes connexionServeur() {
		try {
			IHotelDesVentes hotelDesVentes = (IHotelDesVentes) Naming.lookup("//" + adresseServeur);
			System.out.println("Connexion au serveur " + adresseServeur + " reussi.");
			return hotelDesVentes;
		} catch (Exception e) {
			System.out.println("Connexion au serveur " + adresseServeur + " impossible.");
			e.printStackTrace();
			return null;
		}
	}

	public void inscription() throws Exception {
		/*
		if(!hdv.inscriptionAcheteur(pseudo, this.getInfos() )){
			this.vue.attente();
		}
		*/
	}

	private ClientInfo getInfos() {
		// TODO Auto-generated method stub
		return null;
	}

//	public void encherir(int prix) throws RemoteException, Exception {		
//		if (prix <= this.currentObjet.getPrixCourant() && prix != -1) {
//			System.out.println("Prix trop bas, ne soyez pas radin !");
//		} else if (etat == EtatClient.RENCHERI) {
//			chrono.arreter();
//			vue.attente();
//			etat = EtatClient.ATTENTE;
//			//hdv.rencherir(prix, this.getInfos(), null );
//		}
//	}

//	public void objetVendu(String gagnant) throws RemoteException {
//		//this.currentObjet = hdv.getObjet();
//		this.vue.actualiserObjet();
//		this.vue.reprise();
//		
//		if (gagnant != null) { //Fin de l'objet
//			this.etat = EtatClient.ATTENTE;
//		}else{ //inscription & objet suivant
//			this.etat = EtatClient.RENCHERI;
//			this.chrono.demarrer();
//		}
//	}

	
	public void nouvelleSoumission(String nom, String description, int prix, UUID idSdv) throws RemoteException {
		Objet nouveau = new Objet(nom, description, prix,pseudo);
		//TODO: ajoute objet par le hdv
		hdv.ajouterObjet(nouveau, idSdv);
		//print des informations sur l'ajout
	}

	public static void main(String[] argv) {
		try {
			//start IHM
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public Objet notifyVenteSuivante() {
		//TODO: Doit lancer la vente suivante
		return null;
	}

	public IHotelDesVentes getServeur() {
		return hdv;
	}

	public void setServeur(IHotelDesVentes serveur) {
		this.hdv = serveur;
	}
	
	@Override
	public void notifyNouvelleEnchere (float nouveauPrix, String gagnant, UUID idSalle) {
		ventesSuivies.get(idSalle).setPrixCourant(nouveauPrix);
		ventesSuivies.get(idSalle).setNomGagnant(gagnant);
	}

	@Override
	public void fermetureSalle(UUID idSDV) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void notifyNouvelleVente(Objet nouvelObjet, UUID idSalle) {
		ventesSuivies.put(idSalle, nouvelObjet);
	}
	
	public void rejoindreSalle(UUID idSalle) {

	}
}
