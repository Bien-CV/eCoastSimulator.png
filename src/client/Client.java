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
		this.id = UUID.randomUUID();

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
	
	public void connexion () {
		try {
			hdv.login(id, pseudo);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private ClientInfo getInfos() {
		// TODO Auto-generated method stub
		return null;
	}
	
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
