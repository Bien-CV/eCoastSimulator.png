//TODO:
//notifyNouvelleEnchere donne l'idObjet, le nouveau prix et le nouveau gagnant de l'objet au client

package serveur;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import client.IClient;
import commun.ClientInfo;
import commun.DejaConnecteException;
import commun.DejaDansLaSalleException;
import commun.Objet;
import commun.PasCreateurException;
import commun.PlusDeVenteException;
import commun.PseudoDejaUtiliseException;
import commun.SalleDeVente;
import commun.Message;

@SuppressWarnings("serial")
public class HotelDesVentes extends UnicastRemoteObject implements IHotelDesVentes {
	
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
		if ( fetchedClient == client ){
			ajouterClientASalle(fetchedClient,salleRejointe);
			return salleRejointe.getObjetCourant();
			
		}
		return null;
		
		
	}


public static IClient connexionClient(UUID idClient,String adresseClient) {
	try {
		IClient client = (IClient) Naming.lookup(adresseClient);
		System.out.println("Connexion au serveur " + adresseClient + " reussi.");
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
		SalleDeVente nouvelleSDV=new SalleDeVente(o, nomDeSalle, client.getId());
		listeSalles.add(nouvelleSDV);
		mapSalles.put(nouvelleSDV.getId(), nomDeSalle);
		return nouvelleSDV.getId();
	}

	//Méthode accessible par le client
	@Override
	public void login(ClientInfo client) throws RemoteException, PseudoDejaUtiliseException, DejaConnecteException{
		//TODO Récupération de session 

		//Pas d'homonyme
		for(ClientInfo c : listeClients){
			if (( c.getNom() == client.getNom() ) && ( c.getId()!=client.getId() )){
				throw new PseudoDejaUtiliseException();
			}
		}
		//Pas déjà enregistré
		for(ClientInfo c : listeClients){
			if (c.getId()==client.getId() ){
				throw new DejaConnecteException();
			}
		}
		IClient ref = connexionClient(client.getId(), client.getAdresseClient());
		if (ref != null) {
			listeRefsClient.put(client.getId(), ref);
			//print quel client s'est connecté
			System.out.println(client.getNom().toString()+" -> "+client.getAdresseClient().toString());
			
			listeClients.add(client);
		}
	}


	//Méthode accessible par le client
	//un retour sur le succès ou pas de la méthode serait bienvenu
	@Override
	public void logout(ClientInfo client) {
		//Enlever client de la liste client de l'hdv 
		//Enlever client de chaque salle
		listeClients.remove(client);
		for (SalleDeVente sdv : listeSalles) {
			sdv.retirerClient(client);
		}
	}

	//Méthode accessible par le client
	//Met en vente un objet créé par un client
	@Override
	public void ajouterObjet(Objet objetAVendre, UUID idSDV ) throws RemoteException {
		getSalleById(idSDV).ajouterObjet(objetAVendre);
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
	public void encherir(int prix, UUID clientId, UUID idSDV) throws RemoteException {
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
			if( client.getId()==clientId){
				return client;
			}
		}
		return null;
	}

	// Obtention d'une salle en fonction de son id
	public SalleDeVente getSalleById(UUID roomId) {

		for (SalleDeVente sdv : listeSalles ){
			if ( sdv.getId()==roomId ) return sdv;
		}
		return null;
	}
	
	// suppression salle de vente
	@Override
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
	
	// notification d'une nouvelle vente ou de la fermeture de la salle le cas échéant.
	public void nouvelleVente(UUID idSalle) {
		SalleDeVente SDV = getSalleById(idSalle);
		List<ClientInfo> listeDiffusion = SDV.getListeAcheteurs();
		try {
			SDV.venteSuivante();
			for (ClientInfo ci : listeDiffusion ) {
				listeRefsClient.get(ci.getId()).notifModifObjet(idSalle, SDV.getObjetCourant());
			}
		} catch (PlusDeVenteException e) {
			for (ClientInfo ci : listeDiffusion ) {
				listeRefsClient.get(ci.getId()).notifFermetureSalle(idSalle);
			}
		}
		
	}

	@Override
	public void fermerSalle(UUID idSalle, UUID idClient) throws PasCreateurException {
		SalleDeVente SDV = getSalleById(idSalle);
		List<ClientInfo> listeDiffusion = SDV.getListeAcheteurs();
		if (SDV.getIdCreateur().equals(idClient)) {
			for (ClientInfo ci : listeDiffusion ) {
				listeRefsClient.get(ci.getId()).notifFermetureSalle(idSalle);
			}
		}
		else throw new PasCreateurException();
	}

}
