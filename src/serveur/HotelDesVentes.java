package serveur;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import commun.ClientInfo;
import commun.DebugTools;
import commun.DejaConnecteException;
import commun.DejaDansLaSalleException;
import commun.IClient;
import commun.IHotelDesVentes;
import commun.Objet;
import commun.PasCreateurException;
import commun.PlusDeVenteException;
import commun.PseudoDejaUtiliseException;
import commun.SalleDeVente;
import commun.SalleDeVenteInfo;
import commun.Message;


public class HotelDesVentes extends UnicastRemoteObject implements IHotelDesVentes {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5790188780288793715L;
	private List<SalleDeVente> listeSalles = new ArrayList<SalleDeVente>();
	private List<ClientInfo> listeClients = new ArrayList<ClientInfo>();
	private HashMap<UUID, IClient> listeRefsClient = new HashMap<UUID, IClient>();
	private HashMap<UUID, String> mapSalles = new HashMap<UUID, String>();
	
	protected HotelDesVentes() throws RemoteException {
		super();
	}



	//Méthode accessible par le client
	//Ajoute le client donné à une certaine salle, et renvoie l'Objet en cours d'enchère
	//Fonction partiellement implémentée : authentification
	@Override
	public Objet rejoindreSalle(UUID roomId, ClientInfo client) throws RemoteException {
		ClientInfo fetchedClient=getClientById(client.getId());
		SalleDeVente salleRejointe = getSalleById(roomId);
		if ( fetchedClient.getId().equals(client.getId()) ){
			ajouterClientASalle(fetchedClient,salleRejointe);
			return salleRejointe.getObjetCourant();
		}
		return null;
		
		
	}


	public static IClient connexionClient(UUID idClient,String adresseClient) {
		try {
			
			IClient client = (IClient) Naming.lookup(adresseClient);
			System.out.println("Connexion au serveur " + client.toString() + adresseClient + " reussi.");
			return client;
		} catch (Exception e) {
			System.out.println("Connexion au serveur " + adresseClient + " impossible.");
			e.printStackTrace();
			return null;
		}
	}

	//Méthode accessible par le client
	@Override
	public UUID creerSalle(ClientInfo client, Objet o, String nomDeSalle) throws RemoteException {
		System.out.println("Serveur: le client "+client.getId()+" demande à créer une salle "+nomDeSalle+" avec objet "+o);
		SalleDeVente nouvelleSDV=new SalleDeVente(o, nomDeSalle, client.getId());
		ajouterUneSalle(nouvelleSDV);
		notifCreationSalle(nouvelleSDV.getId());
		TimerVente tv = new TimerVente(o.getDateDeFinDeVente(),this,nouvelleSDV.getId());
		tv.run();
		return nouvelleSDV.getId();
	}

	private void ajouterUneSalle(SalleDeVente nouvelleSDV) {
		listeSalles.add(nouvelleSDV);
		mapSalles.put(nouvelleSDV.getId(), nouvelleSDV.getNom());
		//System.out.println("Liste des salles: "+mapSalles.toString());
	}

	//Méthode accessible par le client
	@Override
	public HashMap<UUID, SalleDeVenteInfo> login(ClientInfo client) throws RemoteException, PseudoDejaUtiliseException, DejaConnecteException{
		System.out.println("Tentative de connexion client.");
		//Pas d'homonyme
		for(ClientInfo c : listeClients){
			if (( c.getNom().equals(client.getNom()) ) && !(c.getId().equals(client.getId()))){
				throw new PseudoDejaUtiliseException();
			}
		}
		//Pas déjà enregistré
		for(ClientInfo c : listeClients){
			if (c.getId().equals(client.getId()) ){
				throw new DejaConnecteException();
			}
		}
		
		IClient ref = connexionClient(client.getId(), client.getAdresseClient());
		if (ref != null) {
			listeRefsClient.put(client.getId(), ref);
			//print quel client s'est connecté
			System.out.println(client.getNom().toString()+" -> "+client.getAdresseClient().toString());
			
			listeClients.add(client);
			return genererListeSalles();
		}
		else{
			System.out.print("ALERTE : connexionClient renvoie null");
			return null;
		}
	}


	//Méthode accessible par le client
	//un retour sur le succès ou pas de la méthode serait bienvenu
	@Override
	public void logout(ClientInfo client) {
		//Enlever client de la liste client de l'hdv 
		//Enlever client de chaque salle
		listeClients.remove(client);
		listeRefsClient.remove(client.getId());
		for (SalleDeVente sdv : listeSalles) {
			sdv.retirerClient(client);
		}
	}

	//Méthode accessible par le client
	//Met en vente un objet créé par un client
	@Override
	public void ajouterObjet(Objet objetAVendre, UUID idSDV, UUID client ) throws RemoteException, PasCreateurException {
		SalleDeVente sdv = getSalleById(idSDV);
		if (sdv.getIdCreateur().equals(client)) 
			sdv.ajouterObjet(objetAVendre);
		else throw new PasCreateurException();
	}

	//Méthode accessible par le client
	// retourne l'objet en vente dans une salle donnée.
	@Override
	public Objet getObjetEnVente(UUID idSDV) throws RemoteException {
		return getSalleById(idSDV).getObjetCourant();
	}

	//Méthode accessible par le client
	// Ajout d'une enchère sur l'objet courant d'une salle donnée.
	@Override
	public void encherir(float prix, UUID clientId, UUID idSDV) throws RemoteException {
		Objet objEnVente = getObjetEnVente(idSDV);

		if(objEnVente.getPrixCourant()<prix){
			if( objEnVente.getNomGagnant()!=getClientById(clientId).getNom()){
				objEnVente.setPrixCourant(prix);
				objEnVente.setNomGagnant(getClientById(clientId).getNom());
				nouvelleEnchere(idSDV, objEnVente);
			}
		}
	}

	//Méthode accessible par le client
	@Override
	public HashMap<UUID, String> getMapSalles() throws RemoteException {
		return mapSalles;
	}
	
	// Ajout d'un client à une salle, s'il n'y est pas déja.
	private void ajouterClientASalle(ClientInfo client, SalleDeVente room) throws RemoteException {
		try {
			room.ajouterClient(client);
		}
		catch (DejaDansLaSalleException e) {

		}
	}

	// Obtention d'un client en fonction de son id.
	private ClientInfo getClientById(UUID clientId) {
		for ( ClientInfo client :listeClients ){
			if( client.getId().equals(clientId)){
				return client;
			}
		}
		return null;
	}

	// Obtention d'une salle en fonction de son id
	public SalleDeVente getSalleById(UUID roomId) {

		for (SalleDeVente sdv : listeSalles ){
			if ( sdv.getId().equals(roomId) ) return sdv;
		}
		return null;
	}
	
	// suppression salle de vente
	public void supprimerSDV(UUID roomID) {
		listeSalles.remove(getSalleById(roomID));
		mapSalles.remove(roomID);
	}


	// diffusion d'un messages aux clients d'une salle dans laquelle il a été posté
	@Override
	public void posterMessage(UUID idSalle, Message message) throws RemoteException {
		SalleDeVente SDV = getSalleById(idSalle);
		SDV.nouveauMessage(message);
		List<ClientInfo> listeDiffusion = SDV.getListeAcheteurs();
		for (ClientInfo ci : listeDiffusion ) {
			listeRefsClient.get(ci.getId()).nouveauMessage(idSalle, message);
		}
	}
	
	// diffusion d'une nouvelle enchere aux clients de la salle concernée
	public void nouvelleEnchere(UUID idSDV, Objet objet) throws RemoteException {
		SalleDeVente SDV = getSalleById(idSDV);
		List<ClientInfo> listeDiffusion = SDV.getListeAcheteurs();
		for (ClientInfo ci : listeDiffusion ) {
			listeRefsClient.get(ci.getId()).notifModifObjet(idSDV, objet);
		}
	}
	
	//Notification : Nouvel objet mis en vente dans salle, portée = Clients présents dans la salle
	//Si la salle n'a plus de ventes à suivre:
	//Notification : fermeture de la salle, portée= tout le monde.
	public void nouvelleVente(UUID idSalle) {
		//TODO:
		SalleDeVente SDV = getSalleById(idSalle);
		try {
			SDV.venteSuivante();
			TimerVente tv = new TimerVente(SDV.getObjetCourant().getDateDeFinDeVente(),this,SDV.getId());
			tv.run();
			for (ClientInfo ci : listeClients ) {
				listeRefsClient.get(ci.getId()).notifModifObjet(idSalle, SDV.getObjetCourant());
			}
		} catch (PlusDeVenteException e) {
			for (ClientInfo ci : listeClients ) {
				try {
					listeRefsClient.get(ci.getId()).notifFermetureSalle(idSalle);
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
			}
			supprimerSDV(idSalle);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
	}

	//Notification : fermeture manuelle d'une salle par un client.
	//Portée : tous les clients de l'hotel des ventes
	@Override
	public void fermerSalle(UUID idSalle, UUID idClient) throws PasCreateurException {
		SalleDeVente SDV = getSalleById(idSalle);
		List<ClientInfo> listeDiffusion = SDV.getListeAcheteurs();
		if (SDV.getIdCreateur().equals(idClient)) {
			for (ClientInfo ci : listeClients ) {
				try {
					listeRefsClient.get(ci.getId()).notifFermetureSalle(idSalle);
				} catch (RemoteException e) {
					e.printStackTrace();
				}
			}
			supprimerSDV(idSalle);
		}
		else throw new PasCreateurException();
	}
	
	// génere une liste des salles de vente avec leur objet courant pour le client.
	public HashMap<UUID, SalleDeVenteInfo> genererListeSalles() {
		HashMap<UUID, SalleDeVenteInfo> salles = new HashMap<UUID, SalleDeVenteInfo>();
		for (SalleDeVente sdv : listeSalles) {
			SalleDeVenteInfo sdvi = new SalleDeVenteInfo(sdv.getNom(), sdv.getId(), sdv.getObjetCourant());
			salles.put(sdv.getId(), sdvi);
		}
		return salles;
	}
	
	// notification aux clients de la création d'unne salle pour permettre la mise à jour de la liste de son coté
	// peut être à remplacer par une fonction "refresh" coté client.
	public void notifCreationSalle (UUID idSalle) {
		SalleDeVente sdv = getSalleById(idSalle);
		SalleDeVenteInfo sdvi = new SalleDeVenteInfo(sdv.getNom(), sdv.getId(), sdv.getObjetCourant());
		System.out.println("Liste des clients : "+listeClients.toString());
		for (ClientInfo ci : listeClients) {
			try {
				DebugTools.d("Envoi notification création salle");
				listeRefsClient.get(ci.getId()).notifNouvelleSalle(idSalle, sdvi);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}


	@Override
	public void quitterSalle(UUID idClient, UUID idSalleAQuitter) throws RemoteException {
		getSalleById(idSalleAQuitter).retirerClient(getClientById(idClient));
	}



	@Override
	public void ping() throws RemoteException {
		System.out.println("Serveur: je reçois un ping");
		
	}



	@Override
	public List<Message> getMessagesSalle(UUID idSalle) {
		return getSalleById(idSalle).getListeMessages();
	}

}
