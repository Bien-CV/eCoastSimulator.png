// TODO :
// notifyNouvelleEnchere
// notifyNouvelleVente

package client;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.UUID;
import java.util.HashMap;
import java.util.List;

import commun.ClientInfo;
import commun.DejaConnecteException;
import commun.Objet;
import commun.PseudoDejaUtiliseException;
import commun.Message;
import serveur.IHotelDesVentes;

public class Client extends UnicastRemoteObject implements IClient {

	private static final long serialVersionUID = 1L;
	private static final String adresseServeur = "localhost:8090/hoteldesventes";
	
	private String adresseClient;
	
	public String getAdresseClient() {
		return adresseClient;
	}

	private String pseudo;
	private IHotelDesVentes hdv;
	private HashMap<UUID, Objet> ventesSuivies;
	// liste des messages postés dans les différentes salles de ventes suivies
	private HashMap<UUID, List<Message>> listesMessages;
	private UUID id;
	private ClientInfo myClientInfos;
	private String ipClient;
	private String portClient;

	public Client(String pseudo) throws RemoteException {
		super();
		this.pseudo = pseudo;
		this.hdv = connexionServeur();
		this.ventesSuivies = new HashMap<UUID, Objet>();
		this.id = UUID.randomUUID();
		
		//TODO: Récupérer la vraie IP du client
		ipClient="localhost";
		
		portClient="8091";
		this.myClientInfos = new ClientInfo(this.id, this.pseudo, ipClient, portClient);
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
			hdv.login(this.myClientInfos);
		} catch (RemoteException | PseudoDejaUtiliseException | DejaConnecteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void deconnexion () {
		try {
			hdv.logout(myClientInfos);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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

	public IHotelDesVentes getServeur() {
		return hdv;
	}

	public void setServeur(IHotelDesVentes serveur) {
		this.hdv = serveur;
	}

	@Override
	public void fermetureSalle(UUID idSDV) {
		// TODO Auto-generated method stub
		
	}
	
	public void rejoindreSalle(UUID idSalle) {

	}
	
	public HashMap<UUID, Objet> getVentesSuivies() {
		return ventesSuivies;
	}

	@Override
	public void nouveauMessage(UUID idSalle, Message message) {
		listesMessages.get(idSalle).add(message);
		// TODO : refresh l'IHM pour prendre en compte les modifs
		// TODO : éventuellement supprimer les plus anciens messages au dela d'un certain nombre.
	}

	@Override
	public void notifModifObjet(UUID idSalle, Objet objet) {
		ventesSuivies.put(idSalle, objet);
		// TODO : refresh l'IHM pour prendre en compte les modifs
	}
}
