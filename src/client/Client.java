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
import commun.PasCreateurException;
import commun.PseudoDejaUtiliseException;
import commun.Message;
import serveur.IHotelDesVentes;

public class Client extends UnicastRemoteObject implements IClient {

	private static final long serialVersionUID = 1L;
	private String urlEtPortServeur;
	private String adresseServeur;
	private String adresseClient;
	
	public String getAdresseClient() {
		return adresseClient;
	}

	private String pseudo;
	public String getPseudo() {
		return pseudo;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	private IHotelDesVentes hdv;
	private HashMap<UUID, Objet> ventesSuivies;
	private HashMap<UUID, Objet> ventesExistantes;
	// liste des messages postés dans les différentes salles de ventes suivies
	private HashMap<UUID, List<Message>> listesMessages;
	private UUID id;
	private ClientInfo myClientInfos;
	private String ipClient;
	private String portClient;

	public Client(String pseudo,String urlEtPortDuServeur) throws RemoteException {
		super();
		this.pseudo = pseudo;
		this.hdv = connexionServeur();
		this.ventesSuivies = new HashMap<UUID, Objet>();
		this.id = UUID.randomUUID();
		
		urlEtPortServeur = urlEtPortDuServeur;
		adresseServeur = urlEtPortServeur + "/hoteldesventes";
		
		
		//TODO: Récupérer la vraie IP du client
		ipClient="localhost";
		
		portClient="8091";
		this.myClientInfos = new ClientInfo(this.id, this.pseudo, ipClient, portClient);
	}

	public static IHotelDesVentes connexionServeur() {
		try {
			IHotelDesVentes hotelDesVentes = (IHotelDesVentes) Naming.lookup("//" + this.adresseServeur);
			System.out.println("Connexion au serveur " + this.adresseServeur + " reussi.");
			return hotelDesVentes;
		} catch (Exception e) {
			System.out.println("Connexion au serveur " + this.adresseServeur + " impossible.");
			e.printStackTrace();
			return null;
		}
	}
	
	// notification au serveur de l'arrivée d'un nouveau client
	public void connexion () {
		try {
			// login + récupération de la liste des salles existantes.
			// TODO : mettre a jour l'IHM avec la liste susmentionnée.
			ventesExistantes = hdv.login(this.myClientInfos);
		} catch (RemoteException | PseudoDejaUtiliseException | DejaConnecteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// notification au serveur du départ d'un client
	public void deconnexion () {
		try {
			hdv.logout(myClientInfos);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO : fermeture de l'application ?
	}
	
	// notification au serveur de l'ajout d'un nouvel objet a vendre dans une salle donnée.
	public void nouvelleSoumission(String nom, String description, int prix, UUID idSdv) throws RemoteException {
		Objet nouveau = new Objet(nom, description, prix,pseudo);
		//ajout de l'objet par le hdv
		// TODO : peut etre autoriser l'ajout seulement pour le créateur de la salle
		try {
			hdv.ajouterObjet(nouveau, idSdv, this.id);
		} catch (PasCreateurException e) {
			// TODO affichage utilisateur en cas d'ajout dans une salle qu'il a pas créé ?
			e.printStackTrace();
		}
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
	
	// notification au serveur de la fermeture d'une salle par le client
	public void fermetureSalle(UUID idSDV) {
		try {
			hdv.fermerSalle(idSDV, this.id);
		} catch (PasCreateurException e) {
			// impossible de fermer la salle si on en est pas le créateur
			e.printStackTrace();
		}
	}
	
	// notification au serveur de la volonté de rejoindre unje nouvelle salle de vente.
	// ajout du nouvel objet suivit en cas de réussite.
	public void rejoindreSalle(UUID idSalle) {
		try {
			Objet obj = hdv.rejoindreSalle(idSalle, this.myClientInfos);
			ventesSuivies.put(idSalle, obj);
			// TODO : refresh l'IHM pour prendre en compte les modifs
		} catch (RemoteException e) {
			e.printStackTrace();
			// TODO : affichage d'un message d'erreur par l'IHM
		}
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

	@Override
	public void notifFermetureSalle(UUID idSalle) {
		ventesSuivies.remove(idSalle);
		// TODO : refresh l'IHM pour prendre en compte les modifs
	}

	@Override
	public void notifNouvelleSalle(UUID idSalle, Objet objEnVente) {
		ventesExistantes.put(idSalle, objEnVente);
	}
}
