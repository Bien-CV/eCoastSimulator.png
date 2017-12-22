//TODO:
//notifyNouvelleEnchere donne l'idObjet, le nouveau prix et le nouveau gagnant de l'objet au client
// faire getSalleByID

package serveur;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import commun.ClientInfo;
import commun.DejaDansLaSalleException;
import commun.InfoSalleDeVente;
import commun.Objet;

@SuppressWarnings("serial")
public class HotelDesVentes extends UnicastRemoteObject implements IHotelDesVentes {
	
	private List<SalleDeVente> listeSalles =new ArrayList<SalleDeVente>();
	private List<ClientInfo> listeClients =new ArrayList<ClientInfo>();
	
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

	//Méthode accessible par le client
	@Override
	public UUID creerSalle(ClientInfo client, Objet o, String nomDeSalle) throws RemoteException {
		SalleDeVente nouvelleSDV=new SalleDeVente(o,nomDeSalle);
		listeSalles.add(nouvelleSDV);
		return nouvelleSDV.getId();
	}

	//Méthode accessible par le client
	@Override
	public boolean login(UUID id, String nomUtilisateur) throws RemoteException{
		//TODO Récupération de session 

		//Pas d'homonyme
		for(ClientInfo c : listeClients){
			if (( c.getNom() == nomUtilisateur ) && ( c.getId()!=id )){
				return false;
			}
		}
		//Pas déjà enregistré
		for(ClientInfo c : listeClients){
			if (c.getId()==id ){
				return false;
			}
		}

		listeClients.add(new ClientInfo(id,nomUtilisateur));
		return true;
	}


	//Méthode accessible par le client
	//un retour sur le succès ou pas de la méthode serait bienvenu
	@Override
	public void logout(ClientInfo client) {
		//Enlever client de la liste client de l'hdv 
		//TODO Enlever client de chaque salle
		listeClients.remove(client);
	}

	//Méthode accessible par le client
	//Met en vente un objet créé par un client
	@Override
	public void ajouterObjet(Objet objetAVendre, UUID idSDV ) throws RemoteException {
		// TODO Auto-generated method stub
		getSalleById(idSDV).ajouterObjet(objetAVendre);
	}

	//Méthode accessible par le client
	@Override
	public Objet getObjetEnVente(UUID idSDV) throws RemoteException {
		// TODO Auto-generated method stub
		
		return getSalleById(idSDV).getObjetCourant();
	}

	//Méthode accessible par le client
	@Override
	public void rencherir(int prix, UUID clientId, UUID idSDV) throws RemoteException {
		// TODO Auto-generated method stub

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
	public ArrayList<InfoSalleDeVente> getListeSalles() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}
	
	private void ajouterClientASalle(ClientInfo client, SalleDeVente room) throws RemoteException {
		try {
			room.ajouterClient(client);
		}
		catch (DejaDansLaSalleException e) {

		}
	}


	private ClientInfo getClientById(UUID clientId) {
		// TODO Auto-generated method stub
		for ( ClientInfo client :listeClients ){
			if( client.getId()==clientId){
				return client;
			}
		}
		return null;
	}


	public SalleDeVente getSalleById(UUID roomId) {

		for (SalleDeVente sdv : listeSalles ){
			if ( sdv.getId()==roomId ) return sdv;
		}
		return null;
		// TODO Auto-generated method stub

	}

}
