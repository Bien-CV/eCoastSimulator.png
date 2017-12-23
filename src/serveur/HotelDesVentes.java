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
import commun.PseudoDejaUtiliseException;
import commun.SalleDeVente;

@SuppressWarnings("serial")
public class HotelDesVentes extends UnicastRemoteObject implements IHotelDesVentes {
	
	private List<SalleDeVente> listeSalles = new ArrayList<SalleDeVente>();
	private List<ClientInfo> listeClients = new ArrayList<ClientInfo>();
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
		SalleDeVente nouvelleSDV=new SalleDeVente(o,nomDeSalle);
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
		connexionClient(client.getId(), client.getAdresseClient());
		
		//print quel client s'est connecté
		System.out.println(client.getNom().toString()+" -> "+client.getAdresseClient().toString());
		
		listeClients.add(client);
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
	public void rencherir(int prix, UUID clientId, UUID idSDV) throws RemoteException {
		Objet objEnVente = getObjetEnVente(idSDV);

		if(objEnVente.getPrixCourant()<prix){
			if( objEnVente.getNomGagnant()!=getClientById(clientId).getNom()){
				objEnVente.setPrixCourant(prix);
				objEnVente.setNomGagnant(getClientById(clientId).getNom());
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
	// TODO : faire la fameuse diffusion
	@Override
	public void posterMessage(UUID idSalle, String pseudo, String message) throws RemoteException {
		SalleDeVente SDV = getSalleById(idSalle);
		SDV.nouveauMessage(pseudo, message);
		List<ClientInfo> listeDiffusion = SDV.getListeAcheteurs();
		for (ClientInfo ci : listeDiffusion ) {
			
		}
	}

}
