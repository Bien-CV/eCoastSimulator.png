package serveur;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SuppressWarnings("serial")
public class HotelDesVentes extends UnicastRemoteObject implements IHotelDesVentes {
	private List<SalleDeVente> listeSalles =new ArrayList<SalleDeVente>();
	
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
		
		//TODO: si cette méthode est reçue du client approprié ( celui qui a pour id clienId)
		if(true){
			getRoomById(roomId);
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
	public SalleDeVente creerSalle(UUID id, Objet o) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void login(String nomUtilisateur) {
		// TODO Auto-generated method stub
		//Récupération de session
		//login par mdp ?
	}


	@Override
	public void logout() {
		// TODO Auto-generated method stub
		//Enlever client de la liste client de l'hdv et ch
		
	}


	@Override
	public void ajouterObjet(Objet nouveau) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public Objet getObjet() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public boolean inscriptionAcheteur(String pseudo, ClientInfo client) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public void rencherir(int prix, ClientInfo client, UUID roomId) {
		// TODO Auto-generated method stub
		
	}


	

}
