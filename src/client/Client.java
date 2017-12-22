// TODO :
// notifyNouvelleEnchere
// notifyNouvelleVente

package client;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.UUID;

import commun.Objet;
import serveur.ClientInfo;
import serveur.IHotelDesVentes;

public class Client extends UnicastRemoteObject {

	private static final long serialVersionUID = 1L;
	private static final String adresseServeur = "localhost:8090/hoteldesventes";

	private String pseudo;
	private VueClient vue;
	private IHotelDesVentes hdv;
	private Objet currentObjet;
	private EtatClient etat = EtatClient.ATTENTE;
	private Chrono chrono = new Chrono(10000, this); // Chrono de 30sc

	public Client(String pseudo) throws RemoteException {
		super();
		this.chrono.start();
		this.pseudo = pseudo;
		this.hdv = connexionServeur();
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

	public void encherir(int prix) throws RemoteException, Exception {		
		if (prix <= this.currentObjet.getPrixCourant() && prix != -1) {
			System.out.println("Prix trop bas, ne soyez pas radin !");
		} else if (etat == EtatClient.RENCHERI) {
			chrono.arreter();
			vue.attente();
			etat = EtatClient.ATTENTE;
			//hdv.rencherir(prix, this.getInfos(), null );
		}
	}

	public void objetVendu(String gagnant) throws RemoteException {
		//this.currentObjet = hdv.getObjet();
		this.vue.actualiserObjet();
		this.vue.reprise();
		
		if (gagnant != null) { //Fin de l'objet
			this.etat = EtatClient.ATTENTE;
		}else{ //inscription & objet suivant
			this.etat = EtatClient.RENCHERI;
			this.chrono.demarrer();
		}
	}

	
	public void nouvelleSoumission(String nom, String description, int prix, UUID idSdv) throws RemoteException {
		Objet nouveau = new Objet(nom, description, prix,pseudo);
		//TODO: ajoute objet par le hdv
		hdv.ajouterObjet(nouveau, null);//null == sdv
		System.out.println("Soumission de l'objet " + nom + " au serveur.");
	}

	public static void main(String[] argv) {
		try {
			new VueClient();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// getters and setters
	public Objet getCurrentObjet() {
		return currentObjet;
	}



	public IHotelDesVentes getServeur() {
		return hdv;
	}

	public void setServeur(IHotelDesVentes serveur) {
		this.hdv = serveur;
	}

	public void setVue(VueClient vueClient) {
		vue = vueClient;
	}

	public EtatClient getEtat() {
		return this.etat;
	}
	

	public void updateChrono(){
		this.vue.updateChrono(this.chrono.getTemps(), this.chrono.getTempsFin());
	}

}
