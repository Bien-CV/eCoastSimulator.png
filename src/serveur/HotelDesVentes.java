package serveur;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SuppressWarnings("serial")
public class HotelDesVentes extends UnicastRemoteObject implements IHotelDesVentes {
	private List<SalleDeVente> listeSalles =new ArrayList<SalleDeVente>();
	private List<ClientInfo> listeClients =new ArrayList<ClientInfo>();
	
	protected HotelDesVentes() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	
	@Override
	public List<SalleDeVente> getListeSalles() throws RemoteException {
		// TODO Auto-generated method stub
		return listeSalles;
	}


	@Override
	public SalleDeVente rejoindreSalle(UUID roomId, UUID clientId) throws RemoteException {
		// TODO Auto-generated method stub
		ClientInfo client=getClientById(clientId);
		//TODO: si cette méthode est reçue du client approprié ( celui qui a pour id clienId)
		if(true){
			putClientInRoom(client,getRoomById(roomId));
		}
		return null;
	}


	private void putClientInRoom(ClientInfo client, SalleDeVente room) throws RemoteException {
		room.getListeAcheteurs().add(client); //add(clientById);
		
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


	private SalleDeVente getRoomById(UUID roomId) {
		
		for (SalleDeVente sdv : listeSalles ){
			 if ( sdv.getId()==roomId ) return sdv;
		 }
		return null;
		// TODO Auto-generated method stub
		
	}


	@Override
	public SalleDeVente creerSalle(ClientInfo client, Objet o) throws RemoteException {
		// TODO Auto-generated method stub
		SalleDeVente nouvelleSDV=new SalleDeVente(o);
		listeSalles.add(nouvelleSDV);
		return nouvelleSDV;
	}


	@Override
	public boolean login(UUID id, String nomUtilisateur) throws RemoteException{
		// TODO Auto-generated method stub
		//Récupération de session
		//login par mdp ?
		
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


	//un retour sur le succès ou pas de la méthode serait bienvenu
	@Override
	public void logout(ClientInfo client) {
		// TODO Auto-generated method stub
		//Enlever client de la liste client de l'hdv et ch
		listeClients.remove(client);
	}


	@Override
	public void ajouterObjet(Objet objetAVendre, SalleDeVente sdv) throws RemoteException {
		// TODO Auto-generated method stub
		sdv.ajouterObjet(objetAVendre);
	}


	@Override
	public Objet getObjetEnVente(SalleDeVente sdv) throws RemoteException {
		// TODO Auto-generated method stub
		return sdv.getListeObjets().get(0);
	}


	@Override
	public void rencherir(int prix, ClientInfo client, SalleDeVente sdv) throws RemoteException {
		// TODO Auto-generated method stub
		
		Objet objEnVente = getObjetEnVente(sdv);
		
		if(objEnVente.getPrixCourant()<prix){
			if( objEnVente.getGagnant()!=client){
				objEnVente.setPrixCourant(prix);
				objEnVente.setGagnant(client);
			}
		}
	}


	

}
