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
	public SalleDeVente rejoindreSalle(UUID id) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public SalleDeVente creerSalle(UUID id, Objet o) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void login(String nomUtilisateur) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void logout() {
		// TODO Auto-generated method stub
		
	}

	

}
